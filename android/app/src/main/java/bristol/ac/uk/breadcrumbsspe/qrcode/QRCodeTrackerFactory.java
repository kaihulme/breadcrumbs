package bristol.ac.uk.breadcrumbsspe.qrcode;

import android.content.Context;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

class QRCodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private Context mContext;

    QRCodeTrackerFactory(Context context) {
        mContext = context;
    }

    @Override
    public Tracker<Barcode> create(Barcode qrcode) {
        return new QRCodeTracker(mContext);
    }
}