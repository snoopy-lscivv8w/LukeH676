package ca.on.sl.comp208.lhahncomp208a3m1;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import java.security.PublicKey;

/**
 * Created by shogo on 2018-04-02.
 */

public class ProviderContract {
    public static final String AUTHORITY = "ca.on.sl.COMP208.A3";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);
    public static UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);

    static{
        matcher.addURI(AUTHORITY,"all",1);
        matcher.addURI(AUTHORITY,"alpha3",2);
    }

    public static final class Data implements BaseColumns {
      //  public static final Uri CONTENT_URI = Uri.withAppendedPath(ProviderContract.CONTENT_URI,);
        public static final String name = "name";
        public static final String alpha2 = "alpha2";
        public static final String alpha3 = "alpha3";

        public static final String[] ALL_COLUMNS =
                {_ID, name, alpha2,alpha3};

    }
}

