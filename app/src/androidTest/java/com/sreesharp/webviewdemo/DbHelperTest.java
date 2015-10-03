package com.sreesharp.webviewdemo;

import android.test.AndroidTestCase;
import com.sreesharp.webviewdemo.models.Entry;
import com.sreesharp.webviewdemo.utilities.DbHelper;
import junit.framework.Assert;
import java.util.List;

//SQLite db access helper class
public class DbHelperTest extends AndroidTestCase {


    public void testInitializeDb()  {
        DbHelper dbHelper = DbHelper.getInstance(getContext());
        Assert.assertTrue(dbHelper != null);
    }

    public void testAddEntry()  {
        DbHelper dbHelper = DbHelper.getInstance(getContext());
        Entry entry = new Entry("Name1", "Value1");
        long id = dbHelper.addEntry(entry);
        Assert.assertTrue(id != -1);
    }

    public void testGetAllEntries()  {
        DbHelper dbHelper = DbHelper.getInstance(getContext());
        List<Entry> allEntries = dbHelper.getAllEntries();
        Assert.assertTrue(allEntries.size() != 0);
    }

}
