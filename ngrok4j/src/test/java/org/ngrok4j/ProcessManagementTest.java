package org.ngrok4j;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ProcessManagementTest {

    @Test
    public void failedStartupWrongBinary(){
        assertThrows(NgrokException.class, () ->
            Ngrok.startClient("date"));
    }

    @Test
    public void failedStartupMissingBinary(){
        assertThrows(NgrokException.class, () ->
            Ngrok.startClient("this does not exist"));
    }

    @Test
    public void failedStartupDoesNotGiveWebConsoleURL(){
        assertThrows(NgrokException.class, () ->
            Ngrok.startClient("this does not exist"));
    }

    @Test
    public void ngrokVersionSuccess(){
        assertTrue(Ngrok.getVersion().length() > 0);
    }

}
