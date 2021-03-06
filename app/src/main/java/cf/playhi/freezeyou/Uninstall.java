package cf.playhi.freezeyou;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import static cf.playhi.freezeyou.Support.checkUpdate;
import static cf.playhi.freezeyou.Support.processAddTranslucent;
import static cf.playhi.freezeyou.Support.processSetTheme;
import static cf.playhi.freezeyou.ToastUtils.showToast;

public class Uninstall extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        processSetTheme(this);
        processAddTranslucent(this);
        super.onCreate(savedInstanceState);
        Support.buildAlertDialog(this, R.mipmap.ic_launcher_round, R.string.removeNoRootCaution, R.string.plsConfirm)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Support.isDeviceOwner(getApplicationContext())) {
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Support.getDevicePolicyManager(getApplicationContext()).clearDeviceOwnerApp("cf.playhi.freezeyou");
                                    showToast(getApplicationContext(), R.string.success);
                                } else {
                                    showToast(getApplicationContext(), R.string.noRootNotActivated);
                                }
                            } catch (Exception e) {
                                showToast(getApplicationContext(), R.string.failed);
                            }
                        } else {
                            showToast(getApplicationContext(), R.string.noRootNotActivated);
                        }
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkUpdate(getApplicationContext());
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                })
                .create()
                .show();
    }
}
