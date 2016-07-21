package com.example.orvilleclarke.testfrag.java.com.example.orvilleclarke.unitTesting;

import com.example.orvilleclarke.testfrag.models.ToDoItem;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;



/**
 * Created by Orville Clarke on 7/11/2016.
 */


public class DatabaseTodoItemUnitTest {

    @Mock
    ToDoItem databaseMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testPropertiesQuery() {
        ToDoItem a = Mockito.mock(ToDoItem.class);

        //ID
        when(a.getId()).thenReturn(3L);
        assertEquals(a.getId(), 3L);

        //NAME
        when(a.getName()).thenReturn("New TODO");
        assertEquals(a.getName(), "New TODO");

        //CREATED ON
        Date testDate = new Date();
        when(a.getCreatedOnDate()).thenReturn(testDate);
        assertEquals(a.getCreatedOnDate(), testDate);

        //TODODUE DATE
        when(a.getToDoDueDate()).thenReturn(testDate);
        assertEquals(a.getToDoDueDate(), testDate);

        //COMPLETED ON DATE
        when(a.getToDoCompleted()).thenReturn(testDate);
        assertEquals(a.getToDoCompleted(), testDate);

        //DESCRIPTION
        when(a.getDescription()).thenReturn("TODO Description");
        assertEquals(a.getDescription(), "TODO Description");

        //PRIORITY
        String LOW = "LOW";
        String MED = "MED";
        String HIGH = "HIGH";
        when(a.getToDoPriority()).thenReturn(LOW);
        assertEquals(a.getToDoPriority(),LOW);

        when(a.getToDoPriority()).thenReturn(MED);
        assertEquals(a.getToDoPriority(),MED);

        when(a.getToDoPriority()).thenReturn(HIGH);
        assertEquals(a.getToDoPriority(),HIGH);

        //Is Due Date set
        when(a.isDueDateSet()).thenReturn(true);
        assertEquals(a.isDueDateSet(),true);

        //Is Completed
        when(a.isCompleted()).thenReturn(false);
        assertEquals(a.isCompleted(), false);


    }

    @Test
    public void test() {


    }
}