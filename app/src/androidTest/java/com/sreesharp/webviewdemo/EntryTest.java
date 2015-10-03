package com.sreesharp.webviewdemo;

import android.test.AndroidTestCase;
import com.sreesharp.webviewdemo.models.Entry;
import junit.framework.Assert;
import java.util.List;


public class EntryTest extends AndroidTestCase {

    public void testgetEntriesFromRawData()  {

        List<Entry> entries = Entry.getEntriesFromRawData("¾name¼value¾name1¼value1");
        Assert.assertTrue(entries.size() == 2);

        entries = Entry.getEntriesFromRawData("¾name¼¾name1¼value1");
        Assert.assertTrue(entries.size() == 2);

        entries = Entry.getEntriesFromRawData("¾name¼¾name1¼");
        Assert.assertTrue(entries.size() == 2);

        entries = Entry.getEntriesFromRawData("¾name¼¾¼");
        Assert.assertTrue(entries.size() == 1);

        entries = Entry.getEntriesFromRawData("¾¼¾¼");
        Assert.assertTrue(entries.size() == 0);
    }
}
