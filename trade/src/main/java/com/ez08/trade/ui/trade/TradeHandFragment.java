//package com.ez08.trade.ui.trade;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.ez08.trade.Constant;
//import com.ez08.trade.R;
//import com.ez08.trade.net.ClientHelper;
//import com.ez08.trade.net.request.BizRequest;
//import com.ez08.trade.tools.DialogUtils;
//import com.ez08.trade.ui.BaseFragment;
//import com.ez08.trade.ui.Interval;
//import com.ez08.trade.user.UserHelper;
//
//import org.json.JSONObject;
//
//import java.util.Iterator;
//import java.util.Set;
//
//public class TradeHandFragment extends BaseFragment implements Interval, View.OnClickListener {
//
//    RelativeLayout bizhong;
//    ImageView flag;
//    TextView moneytype;
//    TextView yingkui;
//    TextView kequ;
//    TextView keyong;
//    TextView shizhi;
//    TextView zongzichan;
//    private OnHandFragmentListener onHandFragmentListener;
//    int position = 0;
//    String[] itemKey = new String[]{"人民币","港元","美元"};
//    String[] itemValue = new String[]{"0","1","2"};
//    int[] itemPic = new int[]{R.drawable.china_3x, R.drawable.usa_3x, R.drawable.china_3x};
//
//    public static TradeHandFragment newInstance() {
//        Bundle args = new Bundle();
//        TradeHandFragment fragment = new TradeHandFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    protected int getLayoutResource() {
//        return R.layout.trade_fragment_hand;
//    }
//
//    @Override
//    protected void onCreateView(View rootView) {
//        bizhong = rootView.findViewById(R.id.bizhong);
//        flag = rootView.findViewById(R.id.flag_country);
//        moneytype = rootView.findViewById(R.id.money_type);
//        yingkui = rootView.findViewById(R.id.yingkui);
//        kequ = rootView.findViewById(R.id.kequ);
//        keyong = rootView.findViewById(R.id.keyong);
//        shizhi = rootView.findViewById(R.id.shizhi);
//        zongzichan = rootView.findViewById(R.id.zongzichan);
//        bizhong.setOnClickListener(this);
//
//        getFunds();
//    }
//
//    private void getFunds(){
//        String body = "FUN=410502&TBL_IN=fundid,moneytype,remark;" + UserHelper.getUser().fundid+","+
//                itemValue[position] + ",;";
//        BizRequest request = new BizRequest();
//        request.setBody(body);
//        request.setCallback((client, data) -> {
//            dismissBusyDialog();
//            if (data.isSucceed()) {
//                Log.e("TradeHandFragment", data.getData());
//                try {
//                    JSONObject jsonObject = new JSONObject(data.getData());
//                    String content = jsonObject.getString("content");
//                    Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                    Set<String> pn = uri.getQueryParameterNames();
//                    for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                        String key = it.next().toString();
//                        if ("TBL_OUT".equals(key)) {
//                            String out = uri.getQueryParameter(key);
//                            String[] split = out.split(";");
//                            String[] var = split[1].split(",");
//
//                            //可用
//                            String fundval = var[6];
//                            //市值
//                            String stkvalue = var[10];
//                            //总值
//                            String marketValue = var[7];
//                            //可取
//                           String kequValue = var[5];
//
//                            keyong.setText(fundval);
//                            zongzichan.setText(marketValue);
//                            shizhi.setText(stkvalue);
//                            kequ.setText(kequValue);
//                        }
//                    }
//                    if (onHandFragmentListener!=null){
//                        onHandFragmentListener.onHandValue(itemValue[position]);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        showBusyDialog();
//        ClientHelper.get().send(request);
//    }
//
//    @Override
//    public void OnPost() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v == bizhong){
//            DialogUtils.showSelectDialog(mContext, itemKey, (dialog, which) -> {
//                if(position != which){
//                    position = which;
//                    flag.setImageDrawable(ContextCompat.getDrawable(mContext,itemPic[position]));
//                    moneytype.setText(itemKey[position]);
//                    if (onHandFragmentListener!=null){
//                        onHandFragmentListener.onHandValue(itemValue[position]);
//                    }
//                    getFunds();
//                }
//            });
//        }
//    }
//
//    public void setYingkui(String yingkuiValue){
//        yingkui.setText(yingkuiValue);
//    }
//
//    public interface OnHandFragmentListener{
//        void onHandValue(String moneytype);
//    }
//
//    public void setOnHandFragmentListener(OnHandFragmentListener onHandFragmentListener) {
//        this.onHandFragmentListener = onHandFragmentListener;
//    }
//}
