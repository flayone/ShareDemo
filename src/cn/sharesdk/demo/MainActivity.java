package cn.sharesdk.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

@SuppressLint("ShowToast") public class MainActivity extends Activity {
	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.text);
		text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showShare();
			}
		});
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.titl));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
		// 启动分享GUI
		oks.show(this);
	}

	/**
	 * 快捷分享项目现在添加为不同的平台添加不同分享内容的方法。 本类用于演示如何区别 微信分享 的分享内容和其他平台分享内容。
	 */

	public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

		public void onShare(Platform platform, ShareParams paramsToShare) {
			
			if (Wechat.NAME.equals(platform.getName())||WechatMoments.NAME.equals(platform.getName())) {
				Toast.makeText(MainActivity.this, "微信分享", Toast.LENGTH_LONG);
				String text = platform.getContext().getString(R.string.action_settings);
				paramsToShare.setTitle("测试标题");
				paramsToShare.setText("测试微信分享链接");
				paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
				// 内置图片 获取图片的bitmap 大小不要超过10M
				paramsToShare.setImageData(((BitmapDrawable) getResources().getDrawable(R.drawable.head)).getBitmap());
				// 网络图片 大小不要超过10k
//				paramsToShare.setImageUrl("https://avatars3.githubusercontent.com/u/11844888?v=3&u=c863a339b87d7a13ec318863f6006072d6bd522e&s=140");
				paramsToShare.setUrl("http://flayone.github.io/");

			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
