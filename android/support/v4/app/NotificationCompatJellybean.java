package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class NotificationCompatJellybean {
  static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
  
  static final String EXTRA_DATA_ONLY_REMOTE_INPUTS = "android.support.dataRemoteInputs";
  
  private static final String KEY_ACTION_INTENT = "actionIntent";
  
  private static final String KEY_ALLOWED_DATA_TYPES = "allowedDataTypes";
  
  private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
  
  private static final String KEY_CHOICES = "choices";
  
  private static final String KEY_DATA_ONLY_REMOTE_INPUTS = "dataOnlyRemoteInputs";
  
  private static final String KEY_EXTRAS = "extras";
  
  private static final String KEY_ICON = "icon";
  
  private static final String KEY_LABEL = "label";
  
  private static final String KEY_REMOTE_INPUTS = "remoteInputs";
  
  private static final String KEY_RESULT_KEY = "resultKey";
  
  private static final String KEY_TITLE = "title";
  
  public static final String TAG = "NotificationCompat";
  
  private static Class<?> sActionClass;
  
  private static Field sActionIconField;
  
  private static Field sActionIntentField;
  
  private static Field sActionTitleField;
  
  private static boolean sActionsAccessFailed;
  
  private static Field sActionsField;
  
  private static final Object sActionsLock;
  
  private static Field sExtrasField;
  
  private static boolean sExtrasFieldAccessFailed;
  
  private static final Object sExtrasLock = new Object();
  
  static {
    sActionsLock = new Object();
  }
  
  public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> paramList) {
    int i = paramList.size();
    SparseArray<Bundle> sparseArray = null;
    byte b = 0;
    while (b < i) {
      Bundle bundle = paramList.get(b);
      SparseArray<Bundle> sparseArray1 = sparseArray;
      if (bundle != null) {
        sparseArray1 = sparseArray;
        if (sparseArray == null)
          sparseArray1 = new SparseArray(); 
        sparseArray1.put(b, bundle);
      } 
      b++;
      sparseArray = sparseArray1;
    } 
    return sparseArray;
  }
  
  private static boolean ensureActionReflectionReadyLocked() {
    if (sActionsAccessFailed)
      return false; 
    try {
      if (sActionsField == null) {
        Class<?> clazz = Class.forName("android.app.Notification$Action");
        sActionClass = clazz;
        sActionIconField = clazz.getDeclaredField("icon");
        sActionTitleField = sActionClass.getDeclaredField("title");
        sActionIntentField = sActionClass.getDeclaredField("actionIntent");
        Field field = Notification.class.getDeclaredField("actions");
        sActionsField = field;
        field.setAccessible(true);
      } 
    } catch (ClassNotFoundException classNotFoundException) {
      Log.e("NotificationCompat", "Unable to access notification actions", classNotFoundException);
      sActionsAccessFailed = true;
    } catch (NoSuchFieldException noSuchFieldException) {
      Log.e("NotificationCompat", "Unable to access notification actions", noSuchFieldException);
      sActionsAccessFailed = true;
    } 
    return sActionsAccessFailed ^ true;
  }
  
  private static RemoteInput fromBundle(Bundle paramBundle) {
    ArrayList arrayList = paramBundle.getStringArrayList("allowedDataTypes");
    HashSet<String> hashSet = new HashSet();
    if (arrayList != null) {
      Iterator<String> iterator = arrayList.iterator();
      while (iterator.hasNext())
        hashSet.add(iterator.next()); 
    } 
    return new RemoteInput(paramBundle.getString("resultKey"), paramBundle.getCharSequence("label"), paramBundle.getCharSequenceArray("choices"), paramBundle.getBoolean("allowFreeFormInput"), paramBundle.getBundle("extras"), hashSet);
  }
  
  private static RemoteInput[] fromBundleArray(Bundle[] paramArrayOfBundle) {
    if (paramArrayOfBundle == null)
      return null; 
    RemoteInput[] arrayOfRemoteInput = new RemoteInput[paramArrayOfBundle.length];
    for (byte b = 0; b < paramArrayOfBundle.length; b++)
      arrayOfRemoteInput[b] = fromBundle(paramArrayOfBundle[b]); 
    return arrayOfRemoteInput;
  }
  
  public static NotificationCompat.Action getAction(Notification paramNotification, int paramInt) {
    // Byte code:
    //   0: getstatic android/support/v4/app/NotificationCompatJellybean.sActionsLock : Ljava/lang/Object;
    //   3: astore_2
    //   4: aload_2
    //   5: monitorenter
    //   6: aload_0
    //   7: invokestatic getActionObjectsLocked : (Landroid/app/Notification;)[Ljava/lang/Object;
    //   10: astore_3
    //   11: aload_3
    //   12: ifnull -> 107
    //   15: aload_3
    //   16: iload_1
    //   17: aaload
    //   18: astore_3
    //   19: aload_0
    //   20: invokestatic getExtras : (Landroid/app/Notification;)Landroid/os/Bundle;
    //   23: astore_0
    //   24: aload_0
    //   25: ifnull -> 51
    //   28: aload_0
    //   29: ldc 'android.support.actionExtras'
    //   31: invokevirtual getSparseParcelableArray : (Ljava/lang/String;)Landroid/util/SparseArray;
    //   34: astore_0
    //   35: aload_0
    //   36: ifnull -> 51
    //   39: aload_0
    //   40: iload_1
    //   41: invokevirtual get : (I)Ljava/lang/Object;
    //   44: checkcast android/os/Bundle
    //   47: astore_0
    //   48: goto -> 53
    //   51: aconst_null
    //   52: astore_0
    //   53: getstatic android/support/v4/app/NotificationCompatJellybean.sActionIconField : Ljava/lang/reflect/Field;
    //   56: aload_3
    //   57: invokevirtual getInt : (Ljava/lang/Object;)I
    //   60: getstatic android/support/v4/app/NotificationCompatJellybean.sActionTitleField : Ljava/lang/reflect/Field;
    //   63: aload_3
    //   64: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   67: checkcast java/lang/CharSequence
    //   70: getstatic android/support/v4/app/NotificationCompatJellybean.sActionIntentField : Ljava/lang/reflect/Field;
    //   73: aload_3
    //   74: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   77: checkcast android/app/PendingIntent
    //   80: aload_0
    //   81: invokestatic readAction : (ILjava/lang/CharSequence;Landroid/app/PendingIntent;Landroid/os/Bundle;)Landroid/support/v4/app/NotificationCompat$Action;
    //   84: astore_0
    //   85: aload_2
    //   86: monitorexit
    //   87: aload_0
    //   88: areturn
    //   89: astore_0
    //   90: goto -> 111
    //   93: astore_0
    //   94: ldc 'NotificationCompat'
    //   96: ldc 'Unable to access notification actions'
    //   98: aload_0
    //   99: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   102: pop
    //   103: iconst_1
    //   104: putstatic android/support/v4/app/NotificationCompatJellybean.sActionsAccessFailed : Z
    //   107: aload_2
    //   108: monitorexit
    //   109: aconst_null
    //   110: areturn
    //   111: aload_2
    //   112: monitorexit
    //   113: aload_0
    //   114: athrow
    // Exception table:
    //   from	to	target	type
    //   6	11	93	java/lang/IllegalAccessException
    //   6	11	89	finally
    //   19	24	93	java/lang/IllegalAccessException
    //   19	24	89	finally
    //   28	35	93	java/lang/IllegalAccessException
    //   28	35	89	finally
    //   39	48	93	java/lang/IllegalAccessException
    //   39	48	89	finally
    //   53	85	93	java/lang/IllegalAccessException
    //   53	85	89	finally
    //   85	87	89	finally
    //   94	107	89	finally
    //   107	109	89	finally
    //   111	113	89	finally
  }
  
  public static int getActionCount(Notification paramNotification) {
    synchronized (sActionsLock) {
      boolean bool;
      Object[] arrayOfObject = getActionObjectsLocked(paramNotification);
      if (arrayOfObject != null) {
        bool = arrayOfObject.length;
      } else {
        bool = false;
      } 
      return bool;
    } 
  }
  
  static NotificationCompat.Action getActionFromBundle(Bundle paramBundle) {
    boolean bool;
    Bundle bundle = paramBundle.getBundle("extras");
    if (bundle != null) {
      bool = bundle.getBoolean("android.support.allowGeneratedReplies", false);
    } else {
      bool = false;
    } 
    return new NotificationCompat.Action(paramBundle.getInt("icon"), paramBundle.getCharSequence("title"), (PendingIntent)paramBundle.getParcelable("actionIntent"), paramBundle.getBundle("extras"), fromBundleArray(getBundleArrayFromBundle(paramBundle, "remoteInputs")), fromBundleArray(getBundleArrayFromBundle(paramBundle, "dataOnlyRemoteInputs")), bool);
  }
  
  private static Object[] getActionObjectsLocked(Notification paramNotification) {
    synchronized (sActionsLock) {
      if (!ensureActionReflectionReadyLocked())
        return null; 
      try {
        return (Object[])sActionsField.get(paramNotification);
      } catch (IllegalAccessException illegalAccessException) {
        Log.e("NotificationCompat", "Unable to access notification actions", illegalAccessException);
        sActionsAccessFailed = true;
        return null;
      } 
    } 
  }
  
  private static Bundle[] getBundleArrayFromBundle(Bundle paramBundle, String paramString) {
    Parcelable[] arrayOfParcelable = paramBundle.getParcelableArray(paramString);
    if (arrayOfParcelable instanceof Bundle[] || arrayOfParcelable == null)
      return (Bundle[])arrayOfParcelable; 
    Bundle[] arrayOfBundle = Arrays.<Bundle, Parcelable>copyOf(arrayOfParcelable, arrayOfParcelable.length, Bundle[].class);
    paramBundle.putParcelableArray(paramString, (Parcelable[])arrayOfBundle);
    return arrayOfBundle;
  }
  
  static Bundle getBundleForAction(NotificationCompat.Action paramAction) {
    Bundle bundle2;
    Bundle bundle1 = new Bundle();
    bundle1.putInt("icon", paramAction.getIcon());
    bundle1.putCharSequence("title", paramAction.getTitle());
    bundle1.putParcelable("actionIntent", (Parcelable)paramAction.getActionIntent());
    if (paramAction.getExtras() != null) {
      bundle2 = new Bundle(paramAction.getExtras());
    } else {
      bundle2 = new Bundle();
    } 
    bundle2.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    bundle1.putBundle("extras", bundle2);
    bundle1.putParcelableArray("remoteInputs", (Parcelable[])toBundleArray(paramAction.getRemoteInputs()));
    return bundle1;
  }
  
  public static Bundle getExtras(Notification paramNotification) {
    synchronized (sExtrasLock) {
      if (sExtrasFieldAccessFailed)
        return null; 
      try {
        if (sExtrasField == null) {
          Field field = Notification.class.getDeclaredField("extras");
          if (!Bundle.class.isAssignableFrom(field.getType())) {
            Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
            sExtrasFieldAccessFailed = true;
            return null;
          } 
          field.setAccessible(true);
          sExtrasField = field;
        } 
        Bundle bundle2 = (Bundle)sExtrasField.get(paramNotification);
        Bundle bundle1 = bundle2;
        if (bundle2 == null) {
          bundle1 = new Bundle();
          this();
          sExtrasField.set(paramNotification, bundle1);
        } 
        return bundle1;
      } catch (IllegalAccessException illegalAccessException) {
        Log.e("NotificationCompat", "Unable to access notification extras", illegalAccessException);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("NotificationCompat", "Unable to access notification extras", noSuchFieldException);
      } 
      sExtrasFieldAccessFailed = true;
      return null;
    } 
  }
  
  public static NotificationCompat.Action readAction(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle) {
    RemoteInput[] arrayOfRemoteInput1;
    boolean bool;
    RemoteInput[] arrayOfRemoteInput2;
    if (paramBundle != null) {
      arrayOfRemoteInput1 = fromBundleArray(getBundleArrayFromBundle(paramBundle, "android.support.remoteInputs"));
      RemoteInput[] arrayOfRemoteInput = fromBundleArray(getBundleArrayFromBundle(paramBundle, "android.support.dataRemoteInputs"));
      bool = paramBundle.getBoolean("android.support.allowGeneratedReplies");
      arrayOfRemoteInput2 = arrayOfRemoteInput1;
      arrayOfRemoteInput1 = arrayOfRemoteInput;
    } else {
      RemoteInput[] arrayOfRemoteInput = null;
      arrayOfRemoteInput1 = arrayOfRemoteInput;
      bool = false;
      arrayOfRemoteInput2 = arrayOfRemoteInput;
    } 
    return new NotificationCompat.Action(paramInt, paramCharSequence, paramPendingIntent, paramBundle, arrayOfRemoteInput2, arrayOfRemoteInput1, bool);
  }
  
  private static Bundle toBundle(RemoteInput paramRemoteInput) {
    Bundle bundle = new Bundle();
    bundle.putString("resultKey", paramRemoteInput.getResultKey());
    bundle.putCharSequence("label", paramRemoteInput.getLabel());
    bundle.putCharSequenceArray("choices", paramRemoteInput.getChoices());
    bundle.putBoolean("allowFreeFormInput", paramRemoteInput.getAllowFreeFormInput());
    bundle.putBundle("extras", paramRemoteInput.getExtras());
    Set<String> set = paramRemoteInput.getAllowedDataTypes();
    if (set != null && !set.isEmpty()) {
      ArrayList<String> arrayList = new ArrayList(set.size());
      Iterator<String> iterator = set.iterator();
      while (iterator.hasNext())
        arrayList.add(iterator.next()); 
      bundle.putStringArrayList("allowedDataTypes", arrayList);
    } 
    return bundle;
  }
  
  private static Bundle[] toBundleArray(RemoteInput[] paramArrayOfRemoteInput) {
    if (paramArrayOfRemoteInput == null)
      return null; 
    Bundle[] arrayOfBundle = new Bundle[paramArrayOfRemoteInput.length];
    for (byte b = 0; b < paramArrayOfRemoteInput.length; b++)
      arrayOfBundle[b] = toBundle(paramArrayOfRemoteInput[b]); 
    return arrayOfBundle;
  }
  
  public static Bundle writeActionAndGetExtras(Notification.Builder paramBuilder, NotificationCompat.Action paramAction) {
    paramBuilder.addAction(paramAction.getIcon(), paramAction.getTitle(), paramAction.getActionIntent());
    Bundle bundle = new Bundle(paramAction.getExtras());
    if (paramAction.getRemoteInputs() != null)
      bundle.putParcelableArray("android.support.remoteInputs", (Parcelable[])toBundleArray(paramAction.getRemoteInputs())); 
    if (paramAction.getDataOnlyRemoteInputs() != null)
      bundle.putParcelableArray("android.support.dataRemoteInputs", (Parcelable[])toBundleArray(paramAction.getDataOnlyRemoteInputs())); 
    bundle.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    return bundle;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\NotificationCompatJellybean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */