package org.thunderdog.challegram.ui;

import android.content.Context;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.DoubleHeaderView;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.unsorted.Passcode;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19/02/2019
 * Author: default
 */
public class SettingsLogOutController extends RecyclerViewController implements View.OnClickListener {
  public SettingsLogOutController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public int getId () {
    return R.id.controller_logOut;
  }

  @Override
  public CharSequence getName () {
    return Lang.getString(R.string.LogOut);
  }

  private SettingsAdapter adapter;
  private DoubleHeaderView headerCell;

  @Override
  public View getCustomHeaderCell () {
    return headerCell;
  }

  @Override
  protected boolean needPersistentScrollPosition () {
    return true;
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    DoubleHeaderView headerCell = new DoubleHeaderView(context);
    headerCell.setThemedTextColor(this);
    headerCell.initWithMargin(0, true);
    headerCell.setTitle(getName());
    headerCell.setSubtitle(Lang.getString(R.string.SignOutAlt));
    this.headerCell = headerCell;


    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        view.setIconColorId(item.getId() == R.id.btn_logout ? R.id.theme_color_iconNegative : 0);
      }
    };

    List<ListItem> items = new ArrayList<>();

    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_addAccount, R.drawable.baseline_person_add_24, R.string.SignOutAltAddAccount));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.SignOutAltAddAccountHint));

    if (!Passcode.instance().isEnabled()) {
      items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
      items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_passcode, R.drawable.baseline_lock_24, R.string.SignOutAltPasscode));
      items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
      items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.SignOutAltPasscodeHint));
    }

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_storageUsage, R.drawable.templarian_baseline_broom_24, R.string.SignOutAltClearCache));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.SignOutAltClearCacheHint));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_changePhoneNumber, R.drawable.baseline_sim_card_24, R.string.SignOutAltChangeNumber));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.SignOutAltChangeNumberHint));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_help, R.drawable.baseline_help_24, R.string.SignOutAltHelp));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.SignOutAltHelpHint));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_logout, R.drawable.baseline_delete_forever_24, R.string.LogOut).setTextColorId(R.id.theme_color_textNegative));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.SignOutAltHint2));

    adapter.setItems(items, false);

    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick (View v) {
    switch (v.getId()) {
      case R.id.btn_addAccount: {
        tdlib.ui().addAccount(context, true, false);
        break;
      }
      case R.id.btn_passcode: {
        if (!Passcode.instance().isEnabled()) {
          navigateTo(new PasscodeSetupController(context, tdlib));
        }
        break;
      }
      case R.id.btn_storageUsage: {
        navigateTo(new SettingsCacheController(context, tdlib));
        break;
      }
      case R.id.btn_changePhoneNumber: {
        navigateTo(new SettingsPhoneController(context, tdlib));
        break;
      }
      case R.id.btn_help: {
        tdlib.ui().openSupport(this);
        break;
      }
      case R.id.btn_logout: {
        tdlib.ui().logOut(this, false);
        break;
      }
    }
  }
}