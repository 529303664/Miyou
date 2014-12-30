package utilclass;

import com.luluandroid.miyou.R;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class FragmentTools {

	/**
	 * 切换fragment 不会重复生成fragment 没有的会自动填入
	 * @param curFra 当前正在占用目标layout位置的fragment
	 * @param to 目标置换的fragment
	 * @param placeLayoutId 目标layout位置的id
	 */
	public static void switchContent(Fragment curFra,Fragment to,int placeLayoutId){
		if(curFra == to){
			return;
		}else{
			FragmentTransaction transaction = curFra.getChildFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
			if(!to.isAdded()){
				transaction.hide(curFra).add(placeLayoutId, to).commit();
			}else{
				transaction.hide(curFra).show(to).commit();
			}
		}
	}
	
	public static void switchContent(Fragment curFra,Fragment to){
		if(curFra == to){
			return;
		}else{
			FragmentTransaction transaction = curFra.getChildFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
			if(!to.isAdded()){
				transaction.hide(curFra).add(R.id.create_tiezi_container, to).commit();
			}else{
				transaction.hide(curFra).show(to).commit();
			}
		}
	}
	
	/**可以删除目标fragment
	 * @param tagFragment
	 */
	public static void deleteFragment(Fragment tagFragment){
		tagFragment.getFragmentManager().beginTransaction().remove(tagFragment).commit();
	}
	
}
