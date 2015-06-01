package com.wiseweb.activity;

import com.wiseweb.fragment.TicketDoneFragment;
import com.wiseweb.fragment.TicketNotSpendindFragment;
import com.wiseweb.fragment.TicketWaitPaymentFragment;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyCinemaTicketActivity extends Activity implements OnClickListener {

	private RelativeLayout myCinemaTicketBack;
	private Button ticketEdit;
	private TextView ticketNotSpending, ticketWaitPayment, ticketDone;
	private View ticketNotSpendingView, ticketWaitPaymentView, ticketDoneView;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private TicketWaitPaymentFragment ticketWaitPaymentFragment;
	private TicketDoneFragment ticketDoneFragment;
	private TicketNotSpendindFragment ticketNotSpendindFragment;
	private boolean flag = false;

	// asdsad
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_cinema_ticket);
		init();
		
		//默认显示未付款界面
		ft.replace(R.id.mine_cinema_ticket_content, ticketNotSpendindFragment);
		ft.commit();
		
		// 设置点击事件
		ticketNotSpending.setOnClickListener(this);
		ticketWaitPayment.setOnClickListener(this);
		ticketDone.setOnClickListener(this);
		ticketEdit.setOnClickListener(this);
		myCinemaTicketBack.setOnClickListener(this);
		
	}

	// 初始化
	public void init() {
		ticketWaitPaymentFragment = new TicketWaitPaymentFragment();
		ticketDoneFragment = new TicketDoneFragment();
		ticketNotSpendindFragment = new TicketNotSpendindFragment();
		myCinemaTicketBack = (RelativeLayout) findViewById(R.id.my_cinema_ticket_back);
		ticketNotSpending = (TextView) findViewById(R.id.my_cinema_ticket_not_spending);
		ticketWaitPayment = (TextView) findViewById(R.id.my_cinema_ticket_wait_payment);
		ticketDone = (TextView) findViewById(R.id.my_cinema_ticket_done);
		ticketEdit = (Button) findViewById(R.id.my_cinema_ticket_edit);
		ticketNotSpendingView = findViewById(R.id.my_cinema_ticket_not_spending_view);
		ticketWaitPaymentView = findViewById(R.id.my_cinema_ticket_wait_payment_view);
		ticketDoneView = findViewById(R.id.my_cinema_ticket_done_view);
		fm = getFragmentManager();
		ft = fm.beginTransaction();
	}

	// 相应点击事件
	@Override
	public void onClick(View v) {
		ft = fm.beginTransaction();
		switch (v.getId()) {
		case R.id.my_cinema_ticket_not_spending:// 点击未消费
			ft.replace(R.id.mine_cinema_ticket_content,
					ticketNotSpendindFragment);
			ticketNotSpending.setTextColor(this.getResources().getColor(
					R.color.main_color));
			ticketNotSpendingView.setVisibility(View.VISIBLE);
			
			ticketWaitPayment.setTextColor(Color.BLACK);
			ticketWaitPaymentView.setVisibility(View.INVISIBLE);
			ticketDone.setTextColor(Color.BLACK);
			ticketDoneView.setVisibility(View.INVISIBLE);
			ticketEdit.setVisibility(View.INVISIBLE);
			onDoneClick();
			break;

		case R.id.my_cinema_ticket_wait_payment:// 点击待付款
			ft.replace(R.id.mine_cinema_ticket_content,
					ticketWaitPaymentFragment, "wait");
			ticketWaitPayment.setTextColor(this.getResources().getColor(
					R.color.main_color));
			ticketWaitPaymentView.setVisibility(View.VISIBLE);
			
			ticketNotSpending.setTextColor(Color.BLACK);
			ticketNotSpendingView.setVisibility(View.INVISIBLE);
			ticketDone.setTextColor(Color.BLACK);
			ticketDoneView.setVisibility(View.INVISIBLE);
			ticketEdit.setVisibility(View.VISIBLE);

			if (ticketEdit.getText().toString().equals("完成")) {
				onDoneClick();
			}

			break;

		case R.id.my_cinema_ticket_done:// 点击已完成
			ft.replace(R.id.mine_cinema_ticket_content, ticketDoneFragment,
					"done");
			ticketDone.setTextColor(this.getResources().getColor(
					R.color.main_color));
			ticketDoneView.setVisibility(View.VISIBLE);
			
			ticketNotSpending.setTextColor(Color.BLACK);
			ticketNotSpendingView.setVisibility(View.INVISIBLE);
			ticketWaitPayment.setTextColor(Color.BLACK);
			ticketWaitPaymentView.setVisibility(View.INVISIBLE);
			ticketEdit.setVisibility(View.VISIBLE);

			if (ticketEdit.getText().toString().equals("完成")) {
				onDoneClick();
			}

			break;

		case R.id.my_cinema_ticket_edit:// 点击编辑
			Toast.makeText(this, "点击了编辑按钮。。。", 0).show();

			if (ticketEdit.getText().toString().equals("编辑")) {
				onEditClick();
			} else if (ticketEdit.getText().toString().equals("完成")) {
				onDoneClick();
			}

			break;

		case R.id.my_cinema_ticket_back:// 点击返回
			finish();

			break;
		}
		ft.commit();
	}

	// 当显示编辑的时候
	public void onEditClick() {
		flag = true;
		// 判断当前activity加载的哪个fragment
		if (fm.findFragmentByTag("wait") != null) {
			ticketWaitPaymentFragment.onDelClick(flag);
		}
		if (fm.findFragmentByTag("done") != null) {
			ticketDoneFragment.onDelClick(flag);
		}

		ticketEdit.setText("完成");    
	}

	// 当显示完成的时候
	public void onDoneClick() {
		flag = false;
		System.out.println("flag===========" + flag);
		// 判断当前activity加载的哪个fragment
		if (fm.findFragmentByTag("wait") != null) {
			ticketWaitPaymentFragment.onDelClick(flag);
		}
		if (fm.findFragmentByTag("done") != null) {
			ticketDoneFragment.onDelClick(flag);
		}

		ticketEdit.setText("编辑");
	}
}
