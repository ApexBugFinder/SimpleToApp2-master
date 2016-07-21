package com.example.orvilleclarke.testfrag.java.com.example.orvilleclarke.unitTesting;

import com.example.orvilleclarke.testfrag.models.ToDoItem;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
    public void testQuery() {
        ToDoItem a = Mockito.mock(ToDoItem.class);
        when(a.getId()).thenReturn(3L);
        assertEquals(a.getId(), 3L);

    }

    @Test
    public void test() {


    }
}