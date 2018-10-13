package waterhole.miner.zcash;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 拷贝kernel文件.
 *
 * @author kzw on 2018/03/14.
 */
public final class KernelCopy {

    private static final int BUFFER = 65535;

    private KernelCopy() {
    }

    /**
     * 拷贝kernel.cl文件到app安装目录，在jni层去读取kernel文件并构建openCL program.
     *
     * @param context  上下文对象
     * @param filename kernel文件名
     */
    public static void copy(Context context, String filename) {
        InputStream in = null;
        OutputStream out = null;
        try {
            final File of = new File(context.getDir("execdir", MODE_PRIVATE), filename);
            in = context.getResources().getAssets().open(filename);
            out = new FileOutputStream(of);
            final byte b[] = new byte[BUFFER];
            int sz;
            while ((sz = in.read(b)) > 0) {
                out.write(b, 0, sz);
            }
        } catch (IOException e) {
            Log.e("KernerCopy", e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
