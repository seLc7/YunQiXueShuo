package cheng.yunqixueshuo;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    EditText yearEditText; // 年份输入框
    TextView outTextView;  // 分析结果输出


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // final EditText inputEditText = (EditText) findViewById(R.id.year_et);


        outTextView = (TextView) findViewById(R.id.out_text);
        yearEditText = (EditText) findViewById(R.id.year_edit_text);

        // 软键盘事件监听
        final InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        Button okBtn = (Button) findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = yearEditText.getText().toString();
                if (year.equals("")) {
                    Toast.makeText(getApplicationContext(), "空白是不允许的哟~o(￣ヘ￣o＃),小狗米",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("out", year);
                    // Log.e("test", test.getText().toString());
                    outTextView.setText(reckon(year));
                    manager.hideSoftInputFromWindow(yearEditText.getWindowToken(), 0); // 关闭软键盘
                }
            }
        });
        //他是事实上是

        yearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                // 软键盘回车事件
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    String year = yearEditText.getText().toString();
                    if (year.equals("")) {
                        Toast.makeText(getApplicationContext(), "空白是不允许的哟~o(￣ヘ￣o＃),小狗米",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("out", year);
                        // Log.e("test", test.getText().toString());
                        outTextView.setText(reckon(year));
                    }
                }

                return false;
            }
        });
    }

    private String reckon(String str) {
        String text; // 返回值
        String keyun; // 客运
        String qihuajianhua; // 五运齐化兼化


        int year = Integer.parseInt(str);
        String[] heavenlyStems = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};  //天干
        String[] earthlyBranches = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};  //地支
        String heavenlyStem = heavenlyStems[(year - 4) % 10];  //当年的天干
        String earthlyBranch = earthlyBranches[(year - 4) % 12];  //当年的地支
        switch (heavenlyStem) {
            case "甲":
                keyun = "阳土运";
                qihuajianhua = "土齐木化";
                break;
            case "乙":
                keyun = "阴金运";
                qihuajianhua = "火来兼化";
                break;
            case "丙":
                keyun = "阳水运";
                qihuajianhua = "水齐土化";
                break;
            case "丁":
                keyun = "阴木运";
                qihuajianhua = "金来兼化";
                break;
            case "戊":
                keyun = "阳火运";
                qihuajianhua = "火齐水化";
                break;
            case "己":
                keyun = "阴土运";
                qihuajianhua = "木来兼化";
                break;
            case "庚":
                keyun = "阳金运";
                qihuajianhua = "金齐火化";
                break;
            case "辛":
                keyun = "阴水运";
                qihuajianhua = "土来兼化";
                break;
            case "壬":
                keyun = "阳木运";
                qihuajianhua = "木齐金化";
                break;
            case "葵":
                keyun = "阴火运";
                qihuajianhua = "水来兼化";
                break;
            default:
                keyun = "";
                qihuajianhua = "";
                break;
        }

        text = "推演结果为：" + "\n\n" + "干支：" + heavenlyStem + earthlyBranch + "\n\n" + "客运：" + keyun
                + "\n\n" + "客气：" + keqiGuilv(earthlyBranch) + "\n\n" + "五运齐化兼化：" + qihuajianhua
                + "\n\n" + "六气正化对化：" + zhenghuaduihua(earthlyBranch) + "\n\n" + "上下相临:"
                + shangxiaxianglin(heavenlyStem, earthlyBranch);
        return text;
    }

    /*
    推导客气
     */
    private String keqiGuilv(String earthlyBranch) {
        String keqi = null;  // 返回字段
        int flag = 0;  //标志位
        ArrayList<String> order = new ArrayList<String>(); // 该年客气规律

        String[] keqis = {"太阳寒水", "厥阴风木", "少阴君火", "太阴湿土", "少阳相火", "阳明燥金"};


        if (earthlyBranch.equals("子") || earthlyBranch.equals("午")) {
            flag = 0;
        } else if (earthlyBranch.equals("丑") || earthlyBranch.equals("未")) {
            flag = 1;
        } else if (earthlyBranch.equals("寅") || earthlyBranch.equals("申")) {
            flag = 2;
        } else if (earthlyBranch.equals("卯") || earthlyBranch.equals("酉")) {
            flag = 3;
        } else if (earthlyBranch.equals("辰") || earthlyBranch.equals("戊")) {
            flag = 4;
        } else if (earthlyBranch.equals("巳") || earthlyBranch.equals("亥")) {
            flag = 5;
        }

        for (int i = flag; i < keqis.length; i++) {
            order.add(keqis[i]);
            // Log.i("keqi", keqis[i]);
        }
        for (int i = 0; i < flag; i++) {
            order.add(keqis[i]);
        }

        keqi = order.get(0) + "," + order.get(1) + "," + order.get(2) + "(司天)"
                + "," + order.get(3) + "," + order.get(4) + "," + order.get(5) + "(在泉)";

        return keqi;

    }

    /*
    六气正化对化
     */
    private String zhenghuaduihua(String earthlyBranch) {

        String zhenghuaduihua = null;
        if (earthlyBranch.equals("子") || earthlyBranch.equals("丑") ||
                earthlyBranch.equals("申") || earthlyBranch.equals("卯") ||
                earthlyBranch.equals("辰") || earthlyBranch.equals("巳")) {
            zhenghuaduihua = "对化";
        } else if (earthlyBranch.equals("午") || earthlyBranch.equals("未") ||
                earthlyBranch.equals("寅") || earthlyBranch.equals("酉") ||
                earthlyBranch.equals("戌") || earthlyBranch.equals("亥")) {
            zhenghuaduihua = "正化";
        }
        return zhenghuaduihua;

    }

    /*
    推导上下相临
     */
    private String shangxiaxianglin(String heavenlyStem, String earthlyBranch) {
        String shangxiaxianglin = null;
        String keyun = null;
        String keqi = null;

        // 客运
        if (heavenlyStem.equals("甲") || heavenlyStem.equals("己")) {
            keyun = "土";
        } else if (heavenlyStem.equals("乙") || heavenlyStem.equals("庚")) {
            keyun = "金";
        } else if (heavenlyStem.equals("丙") || heavenlyStem.equals("辛")) {
            keyun = "水";
        } else if (heavenlyStem.equals("丁") || heavenlyStem.equals("壬")) {
            keyun = "木";
        } else if (heavenlyStem.equals("戊") || heavenlyStem.equals("癸")) {
            keyun = "火";
        }

        // 客气
        if (earthlyBranch.equals("子") || earthlyBranch.equals("午")) {
            keqi = "火";
        } else if (earthlyBranch.equals("丑") || earthlyBranch.equals("未")) {
            keqi = "土";
        } else if (earthlyBranch.equals("寅") || earthlyBranch.equals("申")) {
            keqi = "火";
        } else if (earthlyBranch.equals("卯") || earthlyBranch.equals("酉")) {
            keqi = "金";
        } else if (earthlyBranch.equals("辰") || earthlyBranch.equals("戊")) {
            keqi = "水";
        } else if (earthlyBranch.equals("巳") || earthlyBranch.equals("亥")) {
            keqi = "木";
        }

        if (keyun.equals("土") && keqi.equals("火")) {
            shangxiaxianglin = "顺化";
        } else if (keyun.equals("土") && keqi.equals("金")) {
            shangxiaxianglin = "小逆";
        } else if (keyun.equals("土") && keqi.equals("木")) {
            shangxiaxianglin = "天刑";
        } else if (keyun.equals("土") && keqi.equals("水")) {
            shangxiaxianglin = "不和";
        }

        if (keyun.equals("金") && keqi.equals("土")) {
            shangxiaxianglin = "顺化";
        } else if (keyun.equals("金") && keqi.equals("水")) {
            shangxiaxianglin = "小逆";
        } else if (keyun.equals("金") && keqi.equals("火")) {
            shangxiaxianglin = "天刑";
        } else if (keyun.equals("金") && keqi.equals("木")) {
            shangxiaxianglin = "不和";
        }

        if (keyun.equals("水") && keqi.equals("金")) {
            shangxiaxianglin = "顺化";
        } else if (keyun.equals("水") && keqi.equals("木")) {
            shangxiaxianglin = "小逆";
        } else if (keyun.equals("水") && keqi.equals("土")) {
            shangxiaxianglin = "天刑";
        } else if (keyun.equals("水") && keqi.equals("火")) {
            shangxiaxianglin = "不和";
        }

        if (keyun.equals("木") && keqi.equals("水")) {
            shangxiaxianglin = "顺化";
        } else if (keyun.equals("木") && keqi.equals("火")) {
            shangxiaxianglin = "小逆";
        } else if (keyun.equals("木") && keqi.equals("金")) {
            shangxiaxianglin = "天刑";
        } else if (keyun.equals("木") && keqi.equals("土")) {
            shangxiaxianglin = "不和";
        }

        if (keyun.equals("火") && keqi.equals("木")) {
            shangxiaxianglin = "顺化";
        } else if (keyun.equals("火") && keqi.equals("土")) {
            shangxiaxianglin = "小逆";
        } else if (keyun.equals("火") && keqi.equals("水")) {
            shangxiaxianglin = "天刑";
        } else if (keyun.equals("火") && keqi.equals("金")) {
            shangxiaxianglin = "不和";
        }

        if (keyun.equals(keqi)) {
            shangxiaxianglin = "天符";
        }


        return shangxiaxianglin;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
