package bristol.ac.uk.breadcrumbsspe.qrcode;

import android.content.Context;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

class QRCodeTracker extends Tracker<Barcode> {
    private QRcodeGraphicTrackerCallback mListener;

    public interface QRcodeGraphicTrackerCallback {
        void onDetectedQrCode(Barcode qrcode);
    }

    QRCodeTracker(Context listener) {
        mListener = (QRcodeGraphicTrackerCallback) listener;
    }

    @Override
    public void onNewItem(int id, Barcode item) {
        if (item.displayValue != null) {
            mListener.onDetectedQrCode(item);
        }
    }
}
