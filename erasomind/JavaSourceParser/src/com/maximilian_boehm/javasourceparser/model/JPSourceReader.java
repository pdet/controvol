package com.maximilian_boehm.javasourceparser.model;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.maximilian_boehm.javasourceparser.access.struct.JPClass;
import com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotation;
import com.maximilian_boehm.javasourceparser.model.meta.JPClassImpl;
import com.maximilian_boehm.javasourceparser.model.meta.JPFieldImpl;
import com.maximilian_boehm.javasourceparser.model.meta.base.JPAnnotationImpl;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SimpleTreeVisitor;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

/**
 * Extract information using AST
 * Inspired by http://fw-geekycoder.blogspot.de/2012/09/how-to-parse-java-source-code-using.html
 */
public class JPSourceReader {

    private final JavaCompiler compiler;
    private final StandardJavaFileManager fileManager;
    private final boolean bDebug = false;

    public JPSourceReader() {
        compiler = ToolProvider.getSystemJavaCompiler();
        fileManager = compiler.getStandardFileManager(null, null, null);
    }

    /**
     * Parse Source File and extract relevant information
     */
    public JPClass parseJavaSourceFile(File f, JPClassImpl jpClass) throws Exception {
        // get File Objects of given file
        Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjects(f);
        // Get Compilation Task
        JavacTask javac = (JavacTask) compiler.getTask(null, fileManager, null, null, null, fileObjects);
        // Parse Source File
        Iterable<? extends CompilationUnitTree> trees = javac.parse();

        // Start running through tree
        for (CompilationUnitTree tree : trees)
            // use specifically implemented class-visitor
            tree.accept(new ClassVisitor(jpClass), null);

        return jpClass;
    }

    /**
     * Specifically implemented ClassVisitor
     */
    class ClassVisitor extends SimpleTreeVisitor<Void, Void> {

        JPClassImpl jpClass;

        public ClassVisitor(JPClassImpl jpClass) {
            this.jpClass = jpClass;
        }

        /* (non-Javadoc)
         * @see com.sun.source.util.SimpleTreeVisitor#visitCompilationUnit(com.sun.source.tree.CompilationUnitTree, java.lang.Object)
         */
        @Override
        public Void visitCompilationUnit(CompilationUnitTree cut, Void p) {
            // Get Package-Name
            String sPackageName = cut.getPackageName().toString();
            // Set to JPClass
            jpClass.setPackageName(sPackageName);
            // Debug
            debug("Package name: " + sPackageName);

            // Iterate over TypeDeclarations
            for (Tree t : cut.getTypeDecls()) {
                // Is it a ClassTree?
                if (t instanceof ClassTree) {
                    ClassTree ct = (ClassTree) t;
                    // Go on!
                    ct.accept(this, null);
                }
            }
            return super.visitCompilationUnit(cut, p);
        }


        /* (non-Javadoc)
         * @see com.sun.source.util.SimpleTreeVisitor#visitClass(com.sun.source.tree.ClassTree, java.lang.Object)
         */
        @Override
        public Void visitClass(ClassTree ct, Void p) {
            // Get ClassName
            String sClassName = ct.getSimpleName().toString();
            // Get and Convert Annotations
            List<JPAnnotation> listAnnotations = getAnnotations(ct.getModifiers().getAnnotations());

            // Set Data
            jpClass.setClassName(sClassName);
            jpClass.setAnnotations(listAnnotations);

            // Debug
            debug(sClassName);
            debug(listAnnotations);

            // Iterate over declarations
            for (Tree t : ct.getMembers()) {
                // Is it a declaration of a variable?
                if(t instanceof JCVariableDecl) {
                    // yepp, cast it
                    JCVariableDecl var = (JCVariableDecl)t;

                    // retrieve information
                    String sName = var.getName().toString();
                    String sType = var.getType().toString();
                    List<JPAnnotation> listAnnoVar = getAnnotations(var.getModifiers().getAnnotations());

                    // Create a new field and fill it with information
                    JPFieldImpl field = JPModelFactory.createJPField();
                    field.setName(sName);
                    field.setType(sType);
                    field.setAnnotations(listAnnoVar);
                    jpClass.addField(field);

                    // Debug
                    debug("Typ: "+var.getTag()); // VARDEF means definition of a variable
                    debug("Variablennamen: "+sName); // Name der Variablen
                    debug("Datentyp: "+sType); // Datentyp (e.g. List<Employee> oder Date)
                    debug(listAnnotations);
                    debug("---------");
                }
                else
                    debug(t.getClass());
            }
            return super.visitClass(ct, p);
        }
    }
    /**
     * Convert to JPAnnotation-List
     */
    private List<JPAnnotation> getAnnotations(List<? extends AnnotationTree> listAnnotation){
        // create new list
        List<JPAnnotation> listAnno = new ArrayList<JPAnnotation>();
        // iterate over annotations
        for(AnnotationTree ann:listAnnotation){
            String sType = ann.getAnnotationType().toString();

            // Do not handle suppress warnings
            if(sType.equals("SuppressWarnings"))
                continue;

            // debug
            debug("Annotation-Type: "+sType);
            debug("Annotation-Argumente: "+ann.getArguments());
            debug("----------------");

            // create new annotation
            JPAnnotationImpl anno = JPModelFactory.createJPAnnotation();

            anno.setType(sType);
            // Iterate over Arguments
            for(ExpressionTree expr:ann.getArguments()){
                // Is it an assigment?
                if(expr instanceof JCAssign){
                    JCAssign assign = (JCAssign)expr;
                    anno.addAttribute(assign.lhs.toString(), assign.rhs.toString());
                    debug("ASSIGN: "+assign.lhs+"="+assign.rhs);
                }
                // or is it an Literal?
                else if(expr instanceof JCLiteral){
                    JCLiteral lit = (JCLiteral)expr;
                    anno.addAttribute("value", lit.value.toString());
                    debug("LITERAL: "+lit.value);
                } else{
                    new Throwable().printStackTrace();
                }
            }
            // add annotation to list
            listAnno.add(anno);
        }
        // no annotations? return null
        return listAnno.isEmpty() ? null : listAnno;
    }

    private void debug(Object o){
        if(bDebug){
            System.out.println(o);
        }
    }
}