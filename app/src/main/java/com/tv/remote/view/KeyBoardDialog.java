package com.tv.remote.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.tv.remote.R;
import com.tv.remote.net.NetUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 凯阳 on 2015/10/9.
 */
public class KeyBoardDialog extends Dialog{

    @InjectView(R.id.btn_0)
    Button btn_0;
    @InjectView(R.id.btn_1)
    Button btn_1;
    @InjectView(R.id.btn_2)
    Button btn_2;
    @InjectView(R.id.btn_3)
    Button btn_3;
    @InjectView(R.id.btn_4)
    Button btn_4;
    @InjectView(R.id.btn_5)
    Button btn_5;
    @InjectView(R.id.btn_6)
    Button btn_6;
    @InjectView(R.id.btn_7)
    Button btn_7;
    @InjectView(R.id.btn_8)
    Button btn_8;
    @InjectView(R.id.btn_9)
    Button btn_9;

    @InjectView(R.id.btn_q)
    Button btn_q;
    @InjectView(R.id.btn_w)
    Button btn_w;
    @InjectView(R.id.btn_e)
    Button btn_e;
    @InjectView(R.id.btn_r)
    Button btn_r;
    @InjectView(R.id.btn_t)
    Button btn_t;
    @InjectView(R.id.btn_y)
    Button btn_y;
    @InjectView(R.id.btn_u)
    Button btn_u;
    @InjectView(R.id.btn_i)
    Button btn_i;
    @InjectView(R.id.btn_o)
    Button btn_o;
    @InjectView(R.id.btn_p)
    Button btn_p;

    @InjectView(R.id.btn_a)
    Button btn_a;
    @InjectView(R.id.btn_s)
    Button btn_s;
    @InjectView(R.id.btn_d)
    Button btn_d;
    @InjectView(R.id.btn_f)
    Button btn_f;
    @InjectView(R.id.btn_g)
    Button btn_g;
    @InjectView(R.id.btn_h)
    Button btn_h;
    @InjectView(R.id.btn_j)
    Button btn_j;
    @InjectView(R.id.btn_k)
    Button btn_k;
    @InjectView(R.id.btn_l)
    Button btn_l;

    @InjectView(R.id.btn_shift)
    Button btn_shift;
    @InjectView(R.id.btn_z)
    Button btn_z;
    @InjectView(R.id.btn_x)
    Button btn_x;
    @InjectView(R.id.btn_c)
    Button btn_c;
    @InjectView(R.id.btn_v)
    Button btn_v;
    @InjectView(R.id.btn_b)
    Button btn_b;
    @InjectView(R.id.btn_n)
    Button btn_n;
    @InjectView(R.id.btn_m)
    Button btn_m;
    @InjectView(R.id.btn_del)
    Button btn_del;

    @InjectView(R.id.btn_symbol)
    Button btn_symbol;
    @InjectView(R.id.btn_number)
    Button btn_number;
    @InjectView(R.id.btn_comma)
    Button btn_comma;
    @InjectView(R.id.btn_space)
    Button btn_space;
    @InjectView(R.id.btn_period)
    Button btn_period;
    @InjectView(R.id.btn_enter)
    Button btn_enter;
    @InjectView(R.id.btn_hide)
    Button btn_hide;

    private boolean isSymbol = false;
    private boolean isShift = false;
    private String[] alphabet;

    public KeyBoardDialog(Context context) {
        super(context, R.style.MyDialog);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_softkeyboard,null);
        setContentView(contentView);
        ButterKnife.inject(this, contentView);
        initView();
    }

    private void initView() {
        alphabet = getContext().getResources().getStringArray(R.array.array_alphabet);
        btn_q.setText(alphabet[0]);
        btn_w.setText(alphabet[1]);
        btn_e.setText(alphabet[2]);
        btn_r.setText(alphabet[3]);
        btn_t.setText(alphabet[4]);
        btn_y.setText(alphabet[5]);
        btn_u.setText(alphabet[6]);
        btn_i.setText(alphabet[7]);
        btn_o.setText(alphabet[8]);
        btn_p.setText(alphabet[9]);
        btn_a.setText(alphabet[10]);
        btn_s.setText(alphabet[11]);
        btn_d.setText(alphabet[12]);
        btn_f.setText(alphabet[13]);
        btn_g.setText(alphabet[14]);
        btn_h.setText(alphabet[15]);
        btn_j.setText(alphabet[16]);
        btn_k.setText(alphabet[17]);
        btn_l.setText(alphabet[18]);
        btn_z.setText(alphabet[19]);
        btn_x.setText(alphabet[20]);
        btn_c.setText(alphabet[21]);
        btn_v.setText(alphabet[22]);
        btn_b.setText(alphabet[23]);
        btn_n.setText(alphabet[24]);
        btn_m.setText(alphabet[25]);
    }

    @OnClick({R.id.btn_0,R.id.btn_1,R.id.btn_2,
        R.id.btn_3,R.id.btn_4,R.id.btn_5,
        R.id.btn_6,R.id.btn_7,R.id.btn_8,R.id.btn_9})
    public void onClickNumber(View view){
        NetUtils.getInstance().sendKeyText(((Button)view).getText().toString());
    }

    @OnClick({R.id.btn_q,R.id.btn_w,R.id.btn_e,R.id.btn_r,R.id.btn_t,
        R.id.btn_y,R.id.btn_u,R.id.btn_i,R.id.btn_o,R.id.btn_p,
        R.id.btn_a,R.id.btn_s,R.id.btn_d,R.id.btn_f,R.id.btn_g,
        R.id.btn_h,R.id.btn_j,R.id.btn_k,R.id.btn_l,R.id.btn_z,
        R.id.btn_x,R.id.btn_c,R.id.btn_v,R.id.btn_b,R.id.btn_n,R.id.btn_m})
    public void onClickAlphabet(View view) {
        NetUtils.getInstance().sendKeyText(((Button)view).getText().toString());
    }

    @OnClick(R.id.btn_shift)
    public void onClickShift() {
        if (isSymbol) return;
        NetUtils.getInstance().sendFunKey(1);
        isShift = !isShift;
        if (isShift) {
            btn_shift.setTextColor(Color.BLUE);
            btn_q.setText("Q");
            btn_w.setText("W");
            btn_e.setText("E");
            btn_r.setText("R");
            btn_t.setText("T");
            btn_y.setText("Y");
            btn_u.setText("U");
            btn_i.setText("I");
            btn_o.setText("O");
            btn_p.setText("P");
            btn_a.setText("A");
            btn_s.setText("S");
            btn_d.setText("D");
            btn_f.setText("F");
            btn_g.setText("G");
            btn_h.setText("H");
            btn_j.setText("J");
            btn_k.setText("K");
            btn_l.setText("L");
            btn_z.setText("Z");
            btn_x.setText("X");
            btn_c.setText("C");
            btn_v.setText("V");
            btn_b.setText("B");
            btn_n.setText("N");
            btn_m.setText("M");
        }else {
            btn_shift.setTextColor(Color.BLACK);
            btn_q.setText(alphabet[0]);
            btn_w.setText(alphabet[1]);
            btn_e.setText(alphabet[2]);
            btn_r.setText(alphabet[3]);
            btn_t.setText(alphabet[4]);
            btn_y.setText(alphabet[5]);
            btn_u.setText(alphabet[6]);
            btn_i.setText(alphabet[7]);
            btn_o.setText(alphabet[8]);
            btn_p.setText(alphabet[9]);
            btn_a.setText(alphabet[10]);
            btn_s.setText(alphabet[11]);
            btn_d.setText(alphabet[12]);
            btn_f.setText(alphabet[13]);
            btn_g.setText(alphabet[14]);
            btn_h.setText(alphabet[15]);
            btn_j.setText(alphabet[16]);
            btn_k.setText(alphabet[17]);
            btn_l.setText(alphabet[18]);
            btn_z.setText(alphabet[19]);
            btn_x.setText(alphabet[20]);
            btn_c.setText(alphabet[21]);
            btn_v.setText(alphabet[22]);
            btn_b.setText(alphabet[23]);
            btn_n.setText(alphabet[24]);
            btn_m.setText(alphabet[25]);
        }
    }

    @OnClick(R.id.btn_del)
    public void onClickDel() {
        NetUtils.getInstance().sendFunKey(2);
    }

    @OnClick(R.id.btn_symbol)
    public void onClickSymbol() {
        isSymbol = !isSymbol;
        if (isSymbol) {
            btn_symbol.setText("abc");
            btn_shift.setTextColor(Color.BLACK);
            isShift = false;
            btn_q.setText("*");
            btn_w.setText("/");
            btn_e.setText("+");
            btn_r.setText("-");
            btn_t.setText("=");
            btn_y.setText("<");
            btn_u.setText(">");
            btn_i.setText("《》");
            btn_o.setText("{}");
            btn_p.setText("[]");
            btn_a.setText(";");
            btn_s.setText(":");
            btn_d.setText("@");
            btn_f.setText("~");
            btn_g.setText("%");
            btn_h.setText("#");
            btn_j.setText("\"");
            btn_k.setText("()");
            btn_l.setText("'");
            btn_z.setText("——");
            btn_x.setText("_");
            btn_c.setText("...");
            btn_v.setText("!");
            btn_b.setText("?");
            btn_n.setText("&");
            btn_m.setText("\\");
        } else {
            btn_symbol.setText("~*");
            btn_q.setText("q");
            btn_w.setText("w");
            btn_e.setText("e");
            btn_r.setText("r");
            btn_t.setText("t");
            btn_y.setText("y");
            btn_u.setText("u");
            btn_i.setText("i");
            btn_o.setText("o");
            btn_p.setText("p");
            btn_a.setText("a");
            btn_s.setText("s");
            btn_d.setText("d");
            btn_f.setText("f");
            btn_g.setText("g");
            btn_h.setText("h");
            btn_j.setText("j");
            btn_k.setText("k");
            btn_l.setText("l");
            btn_z.setText("z");
            btn_x.setText("x");
            btn_c.setText("c");
            btn_v.setText("v");
            btn_b.setText("b");
            btn_n.setText("n");
            btn_m.setText("m");
        }
    }

    @OnClick(R.id.btn_space)
    public void onClickSpace() {
        NetUtils.getInstance().sendFunKey(3);
    }

    @OnClick(R.id.btn_enter)
    public void onClickEnter() {
        NetUtils.getInstance().sendFunKey(4);
    }

    @OnClick(R.id.btn_hide)
    public void onClickHide() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }
}
