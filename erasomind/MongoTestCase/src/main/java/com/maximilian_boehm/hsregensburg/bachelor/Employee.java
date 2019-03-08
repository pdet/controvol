package com.maximilian_boehm.hsregensburg.bachelor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Key;
import com.google.code.morphia.annotations.AlsoLoad;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.NotSaved;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Transient;
import com.google.code.morphia.utils.IndexDirection;

@SuppressWarnings(value={"unused"})
@Entity(value="hotels", noClassnameStored=true)
public class Employee {


    public Employee(String firstName, String lastName, Key<Employee> manager, boolean salary) {
        this.lastName = lastName;
        this.manager = manager;
        this.salary = salary;
    }

    public Employee() {}

    @Id private ObjectId id;

    @AlsoLoad("name")
    String lastName;

    // only non-null values are stored
    boolean salary = false;

    //references can be saved without automatic loading
    Key<Employee> manager;

    //refs are stored**, and loaded automatically
    @Reference List<Employee> underlings = new ArrayList<Employee>();

    //fields can be renamed
    @Property("started") Date startDate;
    @Property("left") Date endDate;

    //fields can be indexed for better performance
    @Indexed boolean active = false;

    //fields can loaded, but not saved
    @NotSaved String readButNotStored;

    @Indexed(value=IndexDirection.ASC, name="upc", unique=true, dropDups=true)
    private String upcSymbol;

    //fields can be ignored (no load/save)
    @Transient int notStored;

    //not @Transient, will be ignored by Serialization/GWT for example.
    transient boolean stored = true;

    @Embedded("AAAAAA")
    public class Address {
        private String City;
    }
}

