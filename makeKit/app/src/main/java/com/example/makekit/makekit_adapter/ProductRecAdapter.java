package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_bean.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductRecAdapter extends RecyclerView.Adapter<ProductRecAdapter.ViewHolder> {
    Context context;
    DecimalFormat myFormatter;

    //리스트뷰에서는 아이템을 위한 뷰를 보관하는데 이거는 데이터만 보관한다.
    ArrayList<Product> items = new ArrayList<Product>();

    String urlAddrBase;
    private String urlImageReal;

    //클릭이벤트처리 관련 사용자 정의(이 코드없으면 그냥 리사이클러뷰 구조)//////////////////////////////////////////////////////////////////////////
    private AdapterView.OnItemClickListener mListener = null; //참고로 OnItemClickListener는 기존에 있는것과 동일한 이름인데 그냥 같은 이름으로 내가 정의를 했다. (리스트뷰에서는 이게 자동구현되있어서 OnItemClickListener를 구현안하고 호출해서 클릭시 이벤트를 처리할 수 있음)



    public ProductRecAdapter(Context context, ArrayList<Product> items, String urlAddrBase) {
        this.context = context;
        this.items = items;
        this.urlAddrBase=urlAddrBase;
    }
    //뷰홀더
    //뷰홀더 객체는 뷰를 담아두는 역할을 하면서 동시에 뷰에 표시될 데이터를 설정하는 역할을 맡을 수 있습니다.
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        WebView webView;

        public ViewHolder(@NonNull final View itemView) { //뷰홀더는 각각의 아이템을 위한 뷰를 담고있다.
            super(itemView);

            title = itemView.findViewById(R.id.home_ProductName_TV);
            content = itemView.findViewById(R.id.home_ProductPrice_TV);
            webView = itemView.findViewById(R.id.home_Product_WebView);


            //아이템 클릭이벤트처리
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
    }
    @Override //어댑터에서 관리하는 아이템의 개수를 반환
    public int getItemCount() {
        return items.size();
    }


    @NonNull
    @Override //뷰홀더가 만들어지는 시점에 호출되는 메소드(각각의 아이템을 위한 뷰홀더 객체가 처음만들어지는시점)
    //만약에 각각의 아이템을 위한 뷰홀더가 재사용될 수 있는 상태라면 호출되지않음 (그래서 편리함, 이건내생각인데 리스트뷰같은경우는 convertView로 컨트롤해줘야하는데 이건 자동으로해줌)
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_home_rec, viewGroup, false);
        ProductRecAdapter.ViewHolder vh = new ProductRecAdapter.ViewHolder(v);

        return vh; //각각의 아이템을 위한 뷰를 담고있는 뷰홀더객체를 반환한다.(각 아이템을 위한 XML 레이아웃을 이용해 뷰 객체를 만든 후 뷰홀더에 담아 반환
    }


    //각각의 아이템을 위한 뷰의 xml레이아웃과 서로 뭉쳐지는(결합되는) 경우 자동으로 호출( 즉 뷰홀더가 각각의 아이템을 위한 뷰를 담아주기위한 용도인데 뷰와 아이템이 합질때 호출)
    // Replace the contents of a view //적절한 데이터를 가져와 뷰 소유자의 레이아웃을 채우기 위해 사용(뷰홀더에 각 아이템의 데이터를 설정함)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        urlImageReal = urlAddrBase+"image/"+items.get(position).getProductFilename();
        viewHolder.title.setText(items.get(position).getProductName());
        myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(Integer.parseInt(items.get(position).getProductPrice()));
        viewHolder.content.setText(formattedStringPrice+" 원");
        viewHolder.webView.setWebViewClient(new WebViewClient());
        // Enable JavaScript
        viewHolder.webView.getSettings().setJavaScriptEnabled(true);
        viewHolder.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        viewHolder.webView.getSettings().setBuiltInZoomControls(true);
        viewHolder.webView.getSettings().setSupportZoom(true);
        viewHolder.webView.getSettings().setSupportZoom(true); //zoom mode 사용.
        viewHolder.webView.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        viewHolder.webView.setBackgroundColor(0);
        viewHolder.webView.getSettings().setLoadWithOverviewMode(true);
        viewHolder.webView.getSettings().setUseWideViewPort(true);
        viewHolder.webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        viewHolder.webView.setInitialScale(15);
        viewHolder.webView.loadUrl(urlImageReal);

        viewHolder.webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(v.getContext(), ProdutctViewActivity.class);
                intent.putExtra("productNo", items.get(position).getProductNo());
                v.getContext().startActivity(intent);
                return false;
            }
        });
    }


    public Product getItem(int position) {
        return items.get(position);
    }

    //클릭리스너관련
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }


}

