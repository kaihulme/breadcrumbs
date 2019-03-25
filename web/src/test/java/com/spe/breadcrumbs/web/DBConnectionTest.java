package com.spe.breadcrumbs.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.Statement;
//TODO create In-Mem database for testing
//TODO make getConnection no longer a static method

public class DBConnectionTest {
    @InjectMocks private DBConnection dbConnection;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testMockConnection() throws Exception{
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }
}
