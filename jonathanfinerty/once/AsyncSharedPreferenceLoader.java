package jonathanfinerty.once;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

class AsyncSharedPreferenceLoader {
  private final AsyncTask<String, Void, SharedPreferences> asyncTask;
  
  private final Context context;
  
  public AsyncSharedPreferenceLoader(Context paramContext, String paramString) {
    AsyncTask<String, Void, SharedPreferences> asyncTask = new AsyncTask<String, Void, SharedPreferences>() {
        protected SharedPreferences doInBackground(String... param1VarArgs) {
          return AsyncSharedPreferenceLoader.this.context.getSharedPreferences(param1VarArgs[0], 0);
        }
      };
    this.asyncTask = asyncTask;
    this.context = paramContext;
    asyncTask.execute((Object[])new String[] { paramString });
  }
  
  public SharedPreferences get() {
    try {
      return (SharedPreferences)this.asyncTask.get();
    } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {
      return null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\jonathanfinerty\once\AsyncSharedPreferenceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */