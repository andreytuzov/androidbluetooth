package by.mycloud.railway.arduinobluetooth.view;

import android.content.Context;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Andrey on 10.08.2017.
 */

public class AdapterHelper {

    public static final String ATTR_ATTR1 = "attr1";
    public static final String ATTR_ATTR2 = "attr2";

    SimpleAdapter adapter;
    List<Map<String, Object>> data = new ArrayList<>();

    public SimpleAdapter createAdapter(Context context) {

        int layout = android.R.layout.simple_list_item_2;
        String[] from = new String[] {ATTR_ATTR1, ATTR_ATTR2};
        int[] to = new int[] {android.R.id.text1, android.R.id.text2};


        adapter = new SimpleAdapter(context, data, layout, from, to);
        return adapter;
    }

    public void addItem(String text1, String text2) {
        Map<String, Object> m = new HashMap<>();
        m.put(ATTR_ATTR1, text1);
        m.put(ATTR_ATTR2, text2);
        data.add(m);
        adapter.notifyDataSetChanged();
    }

}
