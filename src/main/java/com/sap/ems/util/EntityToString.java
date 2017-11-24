package com.sap.ems.util;

import java.lang.reflect.Field;

public class EntityToString {
    public static String getString(Object o, Class< ? > c )  
    {  
        String result = c.getSimpleName( ) + ":";  
  
        Field[ ] fields = c.getDeclaredFields( );  
  
        for ( Field field : fields )  
        {  
            field.setAccessible( true );  
  
            try  
            {  
                result += field.getName( ) + "=" + field.get( o ) +",\n";  
            }  
            catch ( Exception e )  
            {  
            }  
        }  
        if(result.indexOf( "," )>=0) result = result.substring( 0 , result.length( )-2 );  
        return result;  
    }
}
