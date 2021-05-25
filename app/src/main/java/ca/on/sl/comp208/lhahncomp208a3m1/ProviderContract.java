package ca.on.sl.comp208.lhahncomp208a3m1;

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

    public static final class Data implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ProviderContract.CONTENT_URI, "data");
        public static final String name = "name";
        public static final String alpha2 = "alpha2";
        public static final String alpha3 = "alpha3";

        public static final String[] ALL_COLUMNS =
                {_ID, alpha2,alpha3};

    }
}

