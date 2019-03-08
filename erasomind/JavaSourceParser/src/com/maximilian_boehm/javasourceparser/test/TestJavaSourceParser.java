package com.maximilian_boehm.javasourceparser.test;

import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.maximilian_boehm.javasourceparser.access.JPAccessFactory;
import com.maximilian_boehm.javasourceparser.access.JPHome;
import com.maximilian_boehm.javasourceparser.access.struct.JPClass;
import com.maximilian_boehm.javasourceparser.access.struct.JPField;
import com.maximilian_boehm.javasourceparser.access.struct.base.JPAnnotation;
import com.maximilian_boehm.javasourceparser.model.JPModelFactory;
import com.maximilian_boehm.javasourceparser.model.meta.JPFieldImpl;
import com.maximilian_boehm.javasourceparser.model.meta.base.JPAnnotationImpl;
import com.maximilian_boehm.javasourceparser.test.testdata.TestClass;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.AlsoLoad;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.Embedded;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.Entity;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.Id;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.Indexed;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.NotSaved;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.Property;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.Reference;
import com.maximilian_boehm.javasourceparser.test.testdata.fake_annotations.Transient;
public class TestJavaSourceParser {

    File fJavaSourceFile;

    /**
     * Retrieve Java-Source-File from Test-Data
     */
    @Before
    public void setUp() throws Exception {
        // Get the test-file
        //Employee.class.getResource("Employee.java").getFile();
        String sFile = TestJavaSourceParser.class.getResource("testdata/Locate.txt").getFile();

        // Workaround: Get path to src-directory
        sFile = sFile.replace("JavaSourceParser/bin/com/", "JavaSourceParser/src/com/");
        sFile = sFile.replace("testdata/Locate.txt", "testdata/TestClass.java");

        // Set source file
        fJavaSourceFile = new File(sFile);
    }

    @Test
    public void testJavaSourceParser() throws Exception {
        // Get home
        JPHome  jpHome  = JPAccessFactory.getHome();
        // Parse class
        JPClass jpClass = jpHome.getParsedClass(fJavaSourceFile);

        // Check name of Class
        Assert.assertEquals(jpClass.getClassName(), TestClass.class.getSimpleName());

        // Check Package-Name
        Assert.assertEquals(jpClass.getPackageName(), TestClass.class.getPackage().getName());

        // Check Class-Annotations
        Assert.assertThat(jpClass.getAnnotations(), equalTo(getAnnotations4Clazz()));

        // Check fields
        Assert.assertThat(jpClass.getFields(), equalTo(getFields4Clazz()));

        // Visual methods for comparing
        //        printFields(jpClass.getFields());
        //        printFields(getFields4Clazz());
    }

    /**
     * @return List of Annotations for the given class
     */
    private List<JPAnnotation> getAnnotations4Clazz() {
        // new list
        List<JPAnnotation> listAnnotation = new ArrayList<JPAnnotation>();

        // Run through Annotations (should be only one)
        for(Annotation annotation:TestClass.class.getAnnotations()){
            // Cast to Entity
            Entity entity = (Entity)annotation;
            // Create Annotation
            JPAnnotationImpl anno = JPModelFactory.createJPAnnotation();
            // Set Type
            anno.setType(Entity.class.getSimpleName());
            // Set attributes
            anno.addAttribute("noClassnameStored", String.valueOf(entity.noClassnameStored()));
            anno.addAttribute("value", "\""+entity.value()+"\"");
            listAnnotation.add(anno);
        }
        return listAnnotation;
    }

    /**
     * Map a Annotation to the project specific JPAnnotation
     */
    private JPAnnotation getAnnotationByReflectAnno(Annotation annotation) throws Exception{
        JPAnnotationImpl jpAnno = JPModelFactory.createJPAnnotation();

        if(annotation instanceof AlsoLoad){
            AlsoLoad anno = (AlsoLoad)annotation;
            jpAnno.setType(AlsoLoad.class.getSimpleName());
            jpAnno.addAttribute("value", anno.value());

        } else if(annotation instanceof Property){
            Property anno = (Property)annotation;
            jpAnno.setType(Property.class.getSimpleName());
            jpAnno.addAttribute("value", anno.value());

        }  else if(annotation instanceof Embedded){
            Embedded anno = (Embedded)annotation;
            jpAnno.setType(Embedded.class.getSimpleName());
            jpAnno.addAttribute("value", anno.value());

        } else if(annotation instanceof Id       ){jpAnno.setType(Id        .class.getSimpleName());
        } else if(annotation instanceof Reference){jpAnno.setType(Reference .class.getSimpleName());
        } else if(annotation instanceof Indexed  ){jpAnno.setType(Indexed   .class.getSimpleName());
        } else if(annotation instanceof NotSaved ){jpAnno.setType(NotSaved  .class.getSimpleName());
        } else if(annotation instanceof Transient){jpAnno.setType(Transient .class.getSimpleName());
        } else {throw new Exception("TODO");}

        return jpAnno;
    }


    /**
     * Map the fields from the reflect API to the JavaSourceParser API
     */
    private List<JPField> getFields4Clazz() throws Exception {
        // create new list
        List<JPField> listFields = new ArrayList<JPField>();

        // iterate over all fields
        for(Field field:TestClass.class.getDeclaredFields()){
            // create new field
            JPFieldImpl jpField = JPModelFactory.createJPField();
            // set name
            jpField.setName(field.getName());

            // retrieve data type
            String sType = field.getGenericType().getTypeName();
            // Generic Type
            if(sType.contains(">")){
                String[] split = sType.split("<");
                split[0] = split[0].substring(split[0].lastIndexOf(".")+1);
                split[1] = split[1].substring(split[1].lastIndexOf(".")+1);
                sType = split[0]+"<"+split[1];
            } else {
                // Regular Type, e.g. String. integer don't contain a dot
                if(sType.contains("."))
                    sType = sType.substring(sType.lastIndexOf(".")+1);
            }
            jpField.setType(sType);

            // Add Annotation to field
            for(Annotation annotation:field.getDeclaredAnnotations())
                jpField.addAnnotation(getAnnotationByReflectAnno(annotation));

            // add field to list
            listFields.add(jpField);
        }

        return listFields;
    }

    /**
     * Print the list of fields to output
     */
    @SuppressWarnings(value={"unused"})
    private void printFields(List<JPField> listField) throws Exception{
        // iterate over fields
        for(JPField field:listField){

            // Print the annotation with its' values
            if(field.hasAnnotations())
                for(JPAnnotation anno:field.getAnnotations()){
                    System.out.print("@"+anno.getType());
                    if(anno.hasAttributes()){
                        System.out.print("(");
                        for(String s:anno.getAttributes().keySet())
                            System.out.print(s+"="+anno.getAttributes().get(s));
                        System.out.print(")");
                    }
                    else
                        System.out.println();
                }
            // Print the Fieldname and Fieldtype
            System.out.println(field.getType()+" "+field.getName());
        }

    }
}
