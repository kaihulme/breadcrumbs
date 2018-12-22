package bristol.ac.uk.breadcrumbsspe.qrcode;

import android.content.Context;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Factory for creating a tracker and associated graphic to be associated with a new barcode.  The
 * multi-processor uses this factory to create barcode trackers as needed -- one for each barcode.
 */
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