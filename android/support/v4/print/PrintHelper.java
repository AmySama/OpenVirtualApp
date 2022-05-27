package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PrintHelper {
  public static final int COLOR_MODE_COLOR = 2;
  
  public static final int COLOR_MODE_MONOCHROME = 1;
  
  public static final int ORIENTATION_LANDSCAPE = 1;
  
  public static final int ORIENTATION_PORTRAIT = 2;
  
  public static final int SCALE_MODE_FILL = 2;
  
  public static final int SCALE_MODE_FIT = 1;
  
  private final PrintHelperVersionImpl mImpl;
  
  public PrintHelper(Context paramContext) {
    if (Build.VERSION.SDK_INT >= 24) {
      this.mImpl = new PrintHelperApi24(paramContext);
    } else if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new PrintHelperApi23(paramContext);
    } else if (Build.VERSION.SDK_INT >= 20) {
      this.mImpl = new PrintHelperApi20(paramContext);
    } else if (Build.VERSION.SDK_INT >= 19) {
      this.mImpl = new PrintHelperApi19(paramContext);
    } else {
      this.mImpl = new PrintHelperStub();
    } 
  }
  
  public static boolean systemSupportsPrint() {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 19) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getColorMode() {
    return this.mImpl.getColorMode();
  }
  
  public int getOrientation() {
    return this.mImpl.getOrientation();
  }
  
  public int getScaleMode() {
    return this.mImpl.getScaleMode();
  }
  
  public void printBitmap(String paramString, Bitmap paramBitmap) {
    this.mImpl.printBitmap(paramString, paramBitmap, (OnPrintFinishCallback)null);
  }
  
  public void printBitmap(String paramString, Bitmap paramBitmap, OnPrintFinishCallback paramOnPrintFinishCallback) {
    this.mImpl.printBitmap(paramString, paramBitmap, paramOnPrintFinishCallback);
  }
  
  public void printBitmap(String paramString, Uri paramUri) throws FileNotFoundException {
    this.mImpl.printBitmap(paramString, paramUri, (OnPrintFinishCallback)null);
  }
  
  public void printBitmap(String paramString, Uri paramUri, OnPrintFinishCallback paramOnPrintFinishCallback) throws FileNotFoundException {
    this.mImpl.printBitmap(paramString, paramUri, paramOnPrintFinishCallback);
  }
  
  public void setColorMode(int paramInt) {
    this.mImpl.setColorMode(paramInt);
  }
  
  public void setOrientation(int paramInt) {
    this.mImpl.setOrientation(paramInt);
  }
  
  public void setScaleMode(int paramInt) {
    this.mImpl.setScaleMode(paramInt);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface ColorMode {}
  
  public static interface OnPrintFinishCallback {
    void onFinish();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface Orientation {}
  
  private static class PrintHelperApi19 implements PrintHelperVersionImpl {
    private static final String LOG_TAG = "PrintHelperApi19";
    
    private static final int MAX_PRINT_SIZE = 3500;
    
    int mColorMode = 2;
    
    final Context mContext;
    
    BitmapFactory.Options mDecodeOptions = null;
    
    protected boolean mIsMinMarginsHandlingCorrect = true;
    
    private final Object mLock = new Object();
    
    int mOrientation;
    
    protected boolean mPrintActivityRespectsOrientation = true;
    
    int mScaleMode = 2;
    
    PrintHelperApi19(Context param1Context) {
      this.mContext = param1Context;
    }
    
    private Bitmap convertBitmapForColorMode(Bitmap param1Bitmap, int param1Int) {
      if (param1Int != 1)
        return param1Bitmap; 
      Bitmap bitmap = Bitmap.createBitmap(param1Bitmap.getWidth(), param1Bitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      Paint paint = new Paint();
      ColorMatrix colorMatrix = new ColorMatrix();
      colorMatrix.setSaturation(0.0F);
      paint.setColorFilter((ColorFilter)new ColorMatrixColorFilter(colorMatrix));
      canvas.drawBitmap(param1Bitmap, 0.0F, 0.0F, paint);
      canvas.setBitmap(null);
      return bitmap;
    }
    
    private Matrix getMatrix(int param1Int1, int param1Int2, RectF param1RectF, int param1Int3) {
      Matrix matrix = new Matrix();
      float f1 = param1RectF.width();
      float f2 = param1Int1;
      f1 /= f2;
      if (param1Int3 == 2) {
        f1 = Math.max(f1, param1RectF.height() / param1Int2);
      } else {
        f1 = Math.min(f1, param1RectF.height() / param1Int2);
      } 
      matrix.postScale(f1, f1);
      matrix.postTranslate((param1RectF.width() - f2 * f1) / 2.0F, (param1RectF.height() - param1Int2 * f1) / 2.0F);
      return matrix;
    }
    
    private static boolean isPortrait(Bitmap param1Bitmap) {
      boolean bool;
      if (param1Bitmap.getWidth() <= param1Bitmap.getHeight()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    private Bitmap loadBitmap(Uri param1Uri, BitmapFactory.Options param1Options) throws FileNotFoundException {
      if (param1Uri != null) {
        Context context = this.mContext;
        if (context != null) {
          BitmapFactory.Options options = null;
          try {
            InputStream inputStream = context.getContentResolver().openInputStream(param1Uri);
          } finally {
            param1Uri = null;
          } 
          if (param1Options != null)
            try {
              param1Options.close();
            } catch (IOException iOException) {
              Log.w("PrintHelperApi19", "close fail ", iOException);
            }  
          throw param1Uri;
        } 
      } 
      throw new IllegalArgumentException("bad argument to loadBitmap");
    }
    
    private Bitmap loadConstrainedBitmap(Uri param1Uri) throws FileNotFoundException {
      if (param1Uri != null && this.mContext != null) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        loadBitmap(param1Uri, options);
        int i = options.outWidth;
        int j = options.outHeight;
        if (i > 0 && j > 0) {
          int k = Math.max(i, j);
          int m;
          for (m = 1; k > 3500; m <<= 1)
            k >>>= 1; 
          if (m > 0 && Math.min(i, j) / m > 0)
            synchronized (this.mLock) {
              BitmapFactory.Options options1 = new BitmapFactory.Options();
              this();
              this.mDecodeOptions = options1;
              options1.inMutable = true;
              this.mDecodeOptions.inSampleSize = m;
              options1 = this.mDecodeOptions;
              try {
                null = loadBitmap(param1Uri, options1);
              } finally {
                null = null;
              } 
            }  
        } 
        return null;
      } 
      throw new IllegalArgumentException("bad argument to getScaledBitmap");
    }
    
    private void writeBitmap(final PrintAttributes attributes, final int fittingMode, final Bitmap bitmap, final ParcelFileDescriptor fileDescriptor, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
      final PrintAttributes pdfAttributes;
      if (this.mIsMinMarginsHandlingCorrect) {
        printAttributes = attributes;
      } else {
        printAttributes = copyAttributes(attributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
      } 
      (new AsyncTask<Void, Void, Throwable>() {
          protected Throwable doInBackground(Void... param2VarArgs) {
            try {
              if (cancellationSignal.isCanceled())
                return null; 
              PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument();
              this(PrintHelper.PrintHelperApi19.this.mContext, pdfAttributes);
              Bitmap bitmap = PrintHelper.PrintHelperApi19.this.convertBitmapForColorMode(bitmap, pdfAttributes.getColorMode());
              boolean bool = cancellationSignal.isCanceled();
              if (bool)
                return null; 
              try {
                RectF rectF;
                PdfDocument.Page page = printedPdfDocument.startPage(1);
                if (PrintHelper.PrintHelperApi19.this.mIsMinMarginsHandlingCorrect) {
                  rectF = new RectF();
                  this(page.getInfo().getContentRect());
                } else {
                  PrintedPdfDocument printedPdfDocument1 = new PrintedPdfDocument();
                  this(PrintHelper.PrintHelperApi19.this.mContext, attributes);
                  PdfDocument.Page page1 = printedPdfDocument1.startPage(1);
                  rectF = new RectF();
                  this(page1.getInfo().getContentRect());
                  printedPdfDocument1.finishPage(page1);
                  printedPdfDocument1.close();
                } 
                Matrix matrix = PrintHelper.PrintHelperApi19.this.getMatrix(bitmap.getWidth(), bitmap.getHeight(), rectF, fittingMode);
                if (!PrintHelper.PrintHelperApi19.this.mIsMinMarginsHandlingCorrect) {
                  matrix.postTranslate(rectF.left, rectF.top);
                  page.getCanvas().clipRect(rectF);
                } 
                page.getCanvas().drawBitmap(bitmap, matrix, null);
                printedPdfDocument.finishPage(page);
                bool = cancellationSignal.isCanceled();
                if (bool)
                  return null; 
                FileOutputStream fileOutputStream = new FileOutputStream();
                this(fileDescriptor.getFileDescriptor());
                printedPdfDocument.writeTo(fileOutputStream);
                return null;
              } finally {
                printedPdfDocument.close();
                ParcelFileDescriptor parcelFileDescriptor = fileDescriptor;
                if (parcelFileDescriptor != null)
                  try {
                    fileDescriptor.close();
                  } catch (IOException iOException) {} 
                if (bitmap != bitmap)
                  bitmap.recycle(); 
              } 
            } finally {}
          }
          
          protected void onPostExecute(Throwable param2Throwable) {
            if (cancellationSignal.isCanceled()) {
              writeResultCallback.onWriteCancelled();
            } else if (param2Throwable == null) {
              writeResultCallback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
            } else {
              Log.e("PrintHelperApi19", "Error writing printed content", param2Throwable);
              writeResultCallback.onWriteFailed(null);
            } 
          }
        }).execute((Object[])new Void[0]);
    }
    
    protected PrintAttributes.Builder copyAttributes(PrintAttributes param1PrintAttributes) {
      PrintAttributes.Builder builder = (new PrintAttributes.Builder()).setMediaSize(param1PrintAttributes.getMediaSize()).setResolution(param1PrintAttributes.getResolution()).setMinMargins(param1PrintAttributes.getMinMargins());
      if (param1PrintAttributes.getColorMode() != 0)
        builder.setColorMode(param1PrintAttributes.getColorMode()); 
      return builder;
    }
    
    public int getColorMode() {
      return this.mColorMode;
    }
    
    public int getOrientation() {
      int i = this.mOrientation;
      int j = i;
      if (i == 0)
        j = 1; 
      return j;
    }
    
    public int getScaleMode() {
      return this.mScaleMode;
    }
    
    public void printBitmap(final String jobName, final Bitmap bitmap, final PrintHelper.OnPrintFinishCallback callback) {
      PrintAttributes.MediaSize mediaSize;
      if (bitmap == null)
        return; 
      final int fittingMode = this.mScaleMode;
      PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
      if (isPortrait(bitmap)) {
        mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
      } else {
        mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
      } 
      PrintAttributes printAttributes = (new PrintAttributes.Builder()).setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
      printManager.print(jobName, new PrintDocumentAdapter() {
            private PrintAttributes mAttributes;
            
            public void onFinish() {
              PrintHelper.OnPrintFinishCallback onPrintFinishCallback = callback;
              if (onPrintFinishCallback != null)
                onPrintFinishCallback.onFinish(); 
            }
            
            public void onLayout(PrintAttributes param2PrintAttributes1, PrintAttributes param2PrintAttributes2, CancellationSignal param2CancellationSignal, PrintDocumentAdapter.LayoutResultCallback param2LayoutResultCallback, Bundle param2Bundle) {
              this.mAttributes = param2PrintAttributes2;
              param2LayoutResultCallback.onLayoutFinished((new PrintDocumentInfo.Builder(jobName)).setContentType(1).setPageCount(1).build(), param2PrintAttributes2.equals(param2PrintAttributes1) ^ true);
            }
            
            public void onWrite(PageRange[] param2ArrayOfPageRange, ParcelFileDescriptor param2ParcelFileDescriptor, CancellationSignal param2CancellationSignal, PrintDocumentAdapter.WriteResultCallback param2WriteResultCallback) {
              PrintHelper.PrintHelperApi19.this.writeBitmap(this.mAttributes, fittingMode, bitmap, param2ParcelFileDescriptor, param2CancellationSignal, param2WriteResultCallback);
            }
          }printAttributes);
    }
    
    public void printBitmap(final String jobName, final Uri imageFile, final PrintHelper.OnPrintFinishCallback callback) throws FileNotFoundException {
      PrintDocumentAdapter printDocumentAdapter = new PrintDocumentAdapter() {
          private PrintAttributes mAttributes;
          
          Bitmap mBitmap = null;
          
          AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
          
          private void cancelLoad() {
            synchronized (PrintHelper.PrintHelperApi19.this.mLock) {
              if (PrintHelper.PrintHelperApi19.this.mDecodeOptions != null) {
                PrintHelper.PrintHelperApi19.this.mDecodeOptions.requestCancelDecode();
                PrintHelper.PrintHelperApi19.this.mDecodeOptions = null;
              } 
              return;
            } 
          }
          
          public void onFinish() {
            super.onFinish();
            cancelLoad();
            AsyncTask<Uri, Boolean, Bitmap> asyncTask = this.mLoadBitmap;
            if (asyncTask != null)
              asyncTask.cancel(true); 
            PrintHelper.OnPrintFinishCallback onPrintFinishCallback = callback;
            if (onPrintFinishCallback != null)
              onPrintFinishCallback.onFinish(); 
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
              bitmap.recycle();
              this.mBitmap = null;
            } 
          }
          
          public void onLayout(PrintAttributes param2PrintAttributes1, PrintAttributes param2PrintAttributes2, CancellationSignal param2CancellationSignal, PrintDocumentAdapter.LayoutResultCallback param2LayoutResultCallback, Bundle param2Bundle) {
            // Byte code:
            //   0: aload_0
            //   1: monitorenter
            //   2: aload_0
            //   3: aload_2
            //   4: putfield mAttributes : Landroid/print/PrintAttributes;
            //   7: aload_0
            //   8: monitorexit
            //   9: aload_3
            //   10: invokevirtual isCanceled : ()Z
            //   13: ifeq -> 22
            //   16: aload #4
            //   18: invokevirtual onLayoutCancelled : ()V
            //   21: return
            //   22: aload_0
            //   23: getfield mBitmap : Landroid/graphics/Bitmap;
            //   26: ifnull -> 64
            //   29: aload #4
            //   31: new android/print/PrintDocumentInfo$Builder
            //   34: dup
            //   35: aload_0
            //   36: getfield val$jobName : Ljava/lang/String;
            //   39: invokespecial <init> : (Ljava/lang/String;)V
            //   42: iconst_1
            //   43: invokevirtual setContentType : (I)Landroid/print/PrintDocumentInfo$Builder;
            //   46: iconst_1
            //   47: invokevirtual setPageCount : (I)Landroid/print/PrintDocumentInfo$Builder;
            //   50: invokevirtual build : ()Landroid/print/PrintDocumentInfo;
            //   53: aload_2
            //   54: aload_1
            //   55: invokevirtual equals : (Ljava/lang/Object;)Z
            //   58: iconst_1
            //   59: ixor
            //   60: invokevirtual onLayoutFinished : (Landroid/print/PrintDocumentInfo;Z)V
            //   63: return
            //   64: aload_0
            //   65: new android/support/v4/print/PrintHelper$PrintHelperApi19$3$1
            //   68: dup
            //   69: aload_0
            //   70: aload_3
            //   71: aload_2
            //   72: aload_1
            //   73: aload #4
            //   75: invokespecial <init> : (Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;Landroid/os/CancellationSignal;Landroid/print/PrintAttributes;Landroid/print/PrintAttributes;Landroid/print/PrintDocumentAdapter$LayoutResultCallback;)V
            //   78: iconst_0
            //   79: anewarray android/net/Uri
            //   82: invokevirtual execute : ([Ljava/lang/Object;)Landroid/os/AsyncTask;
            //   85: putfield mLoadBitmap : Landroid/os/AsyncTask;
            //   88: return
            //   89: astore_1
            //   90: aload_0
            //   91: monitorexit
            //   92: aload_1
            //   93: athrow
            // Exception table:
            //   from	to	target	type
            //   2	9	89	finally
            //   90	92	89	finally
          }
          
          public void onWrite(PageRange[] param2ArrayOfPageRange, ParcelFileDescriptor param2ParcelFileDescriptor, CancellationSignal param2CancellationSignal, PrintDocumentAdapter.WriteResultCallback param2WriteResultCallback) {
            PrintHelper.PrintHelperApi19.this.writeBitmap(this.mAttributes, fittingMode, this.mBitmap, param2ParcelFileDescriptor, param2CancellationSignal, param2WriteResultCallback);
          }
        };
      PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
      PrintAttributes.Builder builder = new PrintAttributes.Builder();
      builder.setColorMode(this.mColorMode);
      int i = this.mOrientation;
      if (i == 1 || i == 0) {
        builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
      } else if (i == 2) {
        builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
      } 
      printManager.print(jobName, printDocumentAdapter, builder.build());
    }
    
    public void setColorMode(int param1Int) {
      this.mColorMode = param1Int;
    }
    
    public void setOrientation(int param1Int) {
      this.mOrientation = param1Int;
    }
    
    public void setScaleMode(int param1Int) {
      this.mScaleMode = param1Int;
    }
  }
  
  class null extends PrintDocumentAdapter {
    private PrintAttributes mAttributes;
    
    public void onFinish() {
      PrintHelper.OnPrintFinishCallback onPrintFinishCallback = callback;
      if (onPrintFinishCallback != null)
        onPrintFinishCallback.onFinish(); 
    }
    
    public void onLayout(PrintAttributes param1PrintAttributes1, PrintAttributes param1PrintAttributes2, CancellationSignal param1CancellationSignal, PrintDocumentAdapter.LayoutResultCallback param1LayoutResultCallback, Bundle param1Bundle) {
      this.mAttributes = param1PrintAttributes2;
      param1LayoutResultCallback.onLayoutFinished((new PrintDocumentInfo.Builder(jobName)).setContentType(1).setPageCount(1).build(), param1PrintAttributes2.equals(param1PrintAttributes1) ^ true);
    }
    
    public void onWrite(PageRange[] param1ArrayOfPageRange, ParcelFileDescriptor param1ParcelFileDescriptor, CancellationSignal param1CancellationSignal, PrintDocumentAdapter.WriteResultCallback param1WriteResultCallback) {
      this.this$0.writeBitmap(this.mAttributes, fittingMode, bitmap, param1ParcelFileDescriptor, param1CancellationSignal, param1WriteResultCallback);
    }
  }
  
  class null extends AsyncTask<Void, Void, Throwable> {
    protected Throwable doInBackground(Void... param1VarArgs) {
      try {
        if (cancellationSignal.isCanceled())
          return null; 
        PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument();
        this(this.this$0.mContext, pdfAttributes);
        Bitmap bitmap = this.this$0.convertBitmapForColorMode(bitmap, pdfAttributes.getColorMode());
        boolean bool = cancellationSignal.isCanceled();
        if (bool)
          return null; 
        try {
          RectF rectF;
          PdfDocument.Page page = printedPdfDocument.startPage(1);
          if (this.this$0.mIsMinMarginsHandlingCorrect) {
            rectF = new RectF();
            this(page.getInfo().getContentRect());
          } else {
            PrintedPdfDocument printedPdfDocument1 = new PrintedPdfDocument();
            this(this.this$0.mContext, attributes);
            PdfDocument.Page page1 = printedPdfDocument1.startPage(1);
            rectF = new RectF();
            this(page1.getInfo().getContentRect());
            printedPdfDocument1.finishPage(page1);
            printedPdfDocument1.close();
          } 
          Matrix matrix = this.this$0.getMatrix(bitmap.getWidth(), bitmap.getHeight(), rectF, fittingMode);
          if (!this.this$0.mIsMinMarginsHandlingCorrect) {
            matrix.postTranslate(rectF.left, rectF.top);
            page.getCanvas().clipRect(rectF);
          } 
          page.getCanvas().drawBitmap(bitmap, matrix, null);
          printedPdfDocument.finishPage(page);
          bool = cancellationSignal.isCanceled();
          if (bool)
            return null; 
          FileOutputStream fileOutputStream = new FileOutputStream();
          this(fileDescriptor.getFileDescriptor());
          printedPdfDocument.writeTo(fileOutputStream);
          return null;
        } finally {
          printedPdfDocument.close();
          ParcelFileDescriptor parcelFileDescriptor = fileDescriptor;
          if (parcelFileDescriptor != null)
            try {
              fileDescriptor.close();
            } catch (IOException iOException) {} 
          if (bitmap != bitmap)
            bitmap.recycle(); 
        } 
      } finally {}
    }
    
    protected void onPostExecute(Throwable param1Throwable) {
      if (cancellationSignal.isCanceled()) {
        writeResultCallback.onWriteCancelled();
      } else if (param1Throwable == null) {
        writeResultCallback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
      } else {
        Log.e("PrintHelperApi19", "Error writing printed content", param1Throwable);
        writeResultCallback.onWriteFailed(null);
      } 
    }
  }
  
  class null extends PrintDocumentAdapter {
    private PrintAttributes mAttributes;
    
    Bitmap mBitmap = null;
    
    AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
    
    private void cancelLoad() {
      synchronized (this.this$0.mLock) {
        if (this.this$0.mDecodeOptions != null) {
          this.this$0.mDecodeOptions.requestCancelDecode();
          this.this$0.mDecodeOptions = null;
        } 
        return;
      } 
    }
    
    public void onFinish() {
      super.onFinish();
      cancelLoad();
      AsyncTask<Uri, Boolean, Bitmap> asyncTask = this.mLoadBitmap;
      if (asyncTask != null)
        asyncTask.cancel(true); 
      PrintHelper.OnPrintFinishCallback onPrintFinishCallback = callback;
      if (onPrintFinishCallback != null)
        onPrintFinishCallback.onFinish(); 
      Bitmap bitmap = this.mBitmap;
      if (bitmap != null) {
        bitmap.recycle();
        this.mBitmap = null;
      } 
    }
    
    public void onLayout(PrintAttributes param1PrintAttributes1, PrintAttributes param1PrintAttributes2, CancellationSignal param1CancellationSignal, PrintDocumentAdapter.LayoutResultCallback param1LayoutResultCallback, Bundle param1Bundle) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: aload_2
      //   4: putfield mAttributes : Landroid/print/PrintAttributes;
      //   7: aload_0
      //   8: monitorexit
      //   9: aload_3
      //   10: invokevirtual isCanceled : ()Z
      //   13: ifeq -> 22
      //   16: aload #4
      //   18: invokevirtual onLayoutCancelled : ()V
      //   21: return
      //   22: aload_0
      //   23: getfield mBitmap : Landroid/graphics/Bitmap;
      //   26: ifnull -> 64
      //   29: aload #4
      //   31: new android/print/PrintDocumentInfo$Builder
      //   34: dup
      //   35: aload_0
      //   36: getfield val$jobName : Ljava/lang/String;
      //   39: invokespecial <init> : (Ljava/lang/String;)V
      //   42: iconst_1
      //   43: invokevirtual setContentType : (I)Landroid/print/PrintDocumentInfo$Builder;
      //   46: iconst_1
      //   47: invokevirtual setPageCount : (I)Landroid/print/PrintDocumentInfo$Builder;
      //   50: invokevirtual build : ()Landroid/print/PrintDocumentInfo;
      //   53: aload_2
      //   54: aload_1
      //   55: invokevirtual equals : (Ljava/lang/Object;)Z
      //   58: iconst_1
      //   59: ixor
      //   60: invokevirtual onLayoutFinished : (Landroid/print/PrintDocumentInfo;Z)V
      //   63: return
      //   64: aload_0
      //   65: new android/support/v4/print/PrintHelper$PrintHelperApi19$3$1
      //   68: dup
      //   69: aload_0
      //   70: aload_3
      //   71: aload_2
      //   72: aload_1
      //   73: aload #4
      //   75: invokespecial <init> : (Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;Landroid/os/CancellationSignal;Landroid/print/PrintAttributes;Landroid/print/PrintAttributes;Landroid/print/PrintDocumentAdapter$LayoutResultCallback;)V
      //   78: iconst_0
      //   79: anewarray android/net/Uri
      //   82: invokevirtual execute : ([Ljava/lang/Object;)Landroid/os/AsyncTask;
      //   85: putfield mLoadBitmap : Landroid/os/AsyncTask;
      //   88: return
      //   89: astore_1
      //   90: aload_0
      //   91: monitorexit
      //   92: aload_1
      //   93: athrow
      // Exception table:
      //   from	to	target	type
      //   2	9	89	finally
      //   90	92	89	finally
    }
    
    public void onWrite(PageRange[] param1ArrayOfPageRange, ParcelFileDescriptor param1ParcelFileDescriptor, CancellationSignal param1CancellationSignal, PrintDocumentAdapter.WriteResultCallback param1WriteResultCallback) {
      this.this$0.writeBitmap(this.mAttributes, fittingMode, this.mBitmap, param1ParcelFileDescriptor, param1CancellationSignal, param1WriteResultCallback);
    }
  }
  
  class null extends AsyncTask<Uri, Boolean, Bitmap> {
    protected Bitmap doInBackground(Uri... param1VarArgs) {
      try {
        return this.this$1.this$0.loadConstrainedBitmap(imageFile);
      } catch (FileNotFoundException fileNotFoundException) {
        return null;
      } 
    }
    
    protected void onCancelled(Bitmap param1Bitmap) {
      layoutResultCallback.onLayoutCancelled();
      this.this$1.mLoadBitmap = null;
    }
    
    protected void onPostExecute(Bitmap param1Bitmap) {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: invokespecial onPostExecute : (Ljava/lang/Object;)V
      //   5: aload_1
      //   6: astore_2
      //   7: aload_1
      //   8: ifnull -> 113
      //   11: aload_0
      //   12: getfield this$1 : Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;
      //   15: getfield this$0 : Landroid/support/v4/print/PrintHelper$PrintHelperApi19;
      //   18: getfield mPrintActivityRespectsOrientation : Z
      //   21: ifeq -> 39
      //   24: aload_1
      //   25: astore_2
      //   26: aload_0
      //   27: getfield this$1 : Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;
      //   30: getfield this$0 : Landroid/support/v4/print/PrintHelper$PrintHelperApi19;
      //   33: getfield mOrientation : I
      //   36: ifne -> 113
      //   39: aload_0
      //   40: monitorenter
      //   41: aload_0
      //   42: getfield this$1 : Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;
      //   45: invokestatic access$500 : (Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;)Landroid/print/PrintAttributes;
      //   48: invokevirtual getMediaSize : ()Landroid/print/PrintAttributes$MediaSize;
      //   51: astore_3
      //   52: aload_0
      //   53: monitorexit
      //   54: aload_1
      //   55: astore_2
      //   56: aload_3
      //   57: ifnull -> 113
      //   60: aload_1
      //   61: astore_2
      //   62: aload_3
      //   63: invokevirtual isPortrait : ()Z
      //   66: aload_1
      //   67: invokestatic access$600 : (Landroid/graphics/Bitmap;)Z
      //   70: if_icmpeq -> 113
      //   73: new android/graphics/Matrix
      //   76: dup
      //   77: invokespecial <init> : ()V
      //   80: astore_2
      //   81: aload_2
      //   82: ldc 90.0
      //   84: invokevirtual postRotate : (F)Z
      //   87: pop
      //   88: aload_1
      //   89: iconst_0
      //   90: iconst_0
      //   91: aload_1
      //   92: invokevirtual getWidth : ()I
      //   95: aload_1
      //   96: invokevirtual getHeight : ()I
      //   99: aload_2
      //   100: iconst_1
      //   101: invokestatic createBitmap : (Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;
      //   104: astore_2
      //   105: goto -> 113
      //   108: astore_1
      //   109: aload_0
      //   110: monitorexit
      //   111: aload_1
      //   112: athrow
      //   113: aload_0
      //   114: getfield this$1 : Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;
      //   117: aload_2
      //   118: putfield mBitmap : Landroid/graphics/Bitmap;
      //   121: aload_2
      //   122: ifnull -> 179
      //   125: new android/print/PrintDocumentInfo$Builder
      //   128: dup
      //   129: aload_0
      //   130: getfield this$1 : Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;
      //   133: getfield val$jobName : Ljava/lang/String;
      //   136: invokespecial <init> : (Ljava/lang/String;)V
      //   139: iconst_1
      //   140: invokevirtual setContentType : (I)Landroid/print/PrintDocumentInfo$Builder;
      //   143: iconst_1
      //   144: invokevirtual setPageCount : (I)Landroid/print/PrintDocumentInfo$Builder;
      //   147: invokevirtual build : ()Landroid/print/PrintDocumentInfo;
      //   150: astore_1
      //   151: aload_0
      //   152: getfield val$newPrintAttributes : Landroid/print/PrintAttributes;
      //   155: aload_0
      //   156: getfield val$oldPrintAttributes : Landroid/print/PrintAttributes;
      //   159: invokevirtual equals : (Ljava/lang/Object;)Z
      //   162: istore #4
      //   164: aload_0
      //   165: getfield val$layoutResultCallback : Landroid/print/PrintDocumentAdapter$LayoutResultCallback;
      //   168: aload_1
      //   169: iconst_1
      //   170: iload #4
      //   172: ixor
      //   173: invokevirtual onLayoutFinished : (Landroid/print/PrintDocumentInfo;Z)V
      //   176: goto -> 187
      //   179: aload_0
      //   180: getfield val$layoutResultCallback : Landroid/print/PrintDocumentAdapter$LayoutResultCallback;
      //   183: aconst_null
      //   184: invokevirtual onLayoutFailed : (Ljava/lang/CharSequence;)V
      //   187: aload_0
      //   188: getfield this$1 : Landroid/support/v4/print/PrintHelper$PrintHelperApi19$3;
      //   191: aconst_null
      //   192: putfield mLoadBitmap : Landroid/os/AsyncTask;
      //   195: return
      // Exception table:
      //   from	to	target	type
      //   41	54	108	finally
      //   109	111	108	finally
    }
    
    protected void onPreExecute() {
      cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            public void onCancel() {
              this.this$2.this$1.cancelLoad();
              PrintHelper.PrintHelperApi19.null.null.this.cancel(false);
            }
          });
    }
  }
  
  class null implements CancellationSignal.OnCancelListener {
    public void onCancel() {
      this.this$2.this$1.cancelLoad();
      this.this$2.cancel(false);
    }
  }
  
  private static class PrintHelperApi20 extends PrintHelperApi19 {
    PrintHelperApi20(Context param1Context) {
      super(param1Context);
    }
  }
  
  private static class PrintHelperApi23 extends PrintHelperApi20 {
    PrintHelperApi23(Context param1Context) {
      super(param1Context);
    }
    
    protected PrintAttributes.Builder copyAttributes(PrintAttributes param1PrintAttributes) {
      PrintAttributes.Builder builder = super.copyAttributes(param1PrintAttributes);
      if (param1PrintAttributes.getDuplexMode() != 0)
        builder.setDuplexMode(param1PrintAttributes.getDuplexMode()); 
      return builder;
    }
  }
  
  private static class PrintHelperApi24 extends PrintHelperApi23 {
    PrintHelperApi24(Context param1Context) {
      super(param1Context);
    }
  }
  
  private static final class PrintHelperStub implements PrintHelperVersionImpl {
    int mColorMode = 2;
    
    int mOrientation = 1;
    
    int mScaleMode = 2;
    
    private PrintHelperStub() {}
    
    public int getColorMode() {
      return this.mColorMode;
    }
    
    public int getOrientation() {
      return this.mOrientation;
    }
    
    public int getScaleMode() {
      return this.mScaleMode;
    }
    
    public void printBitmap(String param1String, Bitmap param1Bitmap, PrintHelper.OnPrintFinishCallback param1OnPrintFinishCallback) {}
    
    public void printBitmap(String param1String, Uri param1Uri, PrintHelper.OnPrintFinishCallback param1OnPrintFinishCallback) {}
    
    public void setColorMode(int param1Int) {
      this.mColorMode = param1Int;
    }
    
    public void setOrientation(int param1Int) {
      this.mOrientation = param1Int;
    }
    
    public void setScaleMode(int param1Int) {
      this.mScaleMode = param1Int;
    }
  }
  
  static interface PrintHelperVersionImpl {
    int getColorMode();
    
    int getOrientation();
    
    int getScaleMode();
    
    void printBitmap(String param1String, Bitmap param1Bitmap, PrintHelper.OnPrintFinishCallback param1OnPrintFinishCallback);
    
    void printBitmap(String param1String, Uri param1Uri, PrintHelper.OnPrintFinishCallback param1OnPrintFinishCallback) throws FileNotFoundException;
    
    void setColorMode(int param1Int);
    
    void setOrientation(int param1Int);
    
    void setScaleMode(int param1Int);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface ScaleMode {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\print\PrintHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */