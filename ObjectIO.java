package com.ExodiaSolutions.sunnynarang.itmexodia;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Shriram choubey on 1/8/2017.
 * this class Do Input Output of the object to the file
 * uses callback so that things can be done after reading or writing the object
 */

public class ObjectIO {

    private Context context;
    private WriterListener writerListener;
    private ReaderListener readerListener;

    public ObjectIO(Context context) {
        this.context = context;
    }

	
	// Method Write the object to the file
    public void writeObj( Object object) {
        String filename ="link.db";
        writerListener = (WriterListener) context;
        ObjectWriter objectWriter = new ObjectWriter(context, filename, object);
        objectWriter.execute();
    }

	
	// Method Read Object from the file
    public void readObj() {
        String filename ="link.db";
        readerListener = (ReaderListener) context;
        ObjectReader objectReader = new ObjectReader(context, filename, this);
        objectReader.execute();
    }

	
    private class ObjectWriter extends AsyncTask<String, Void, String> {

        private Context context;
        private String filename;
        private Object object;


        ObjectWriter(Context context, String filename, Object object) {
            this.context = context;
            this.filename = filename;
            this.object = object;
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                File filesDir = context.getFilesDir();
                File f = new File(filesDir, filename);
                FileOutputStream outputStream = new FileOutputStream(f);
                ObjectOutputStream os = new ObjectOutputStream(outputStream);
                os.writeObject(object);
                os.flush();
                outputStream.close();
                os.close();
                return "1";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                writerListener.ObjectWriterCallback(true);
                //Toast.makeText(context, "object written", Toast.LENGTH_SHORT).show();
            } else {
                writerListener.ObjectWriterCallback(false);
                //Toast.makeText(context, "object not Written", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class ObjectReader extends AsyncTask<Object, Void, Object> {

        private Context context;
        private String filename;
        ObjectIO objectio;

        ObjectReader(Context context, String filename, ObjectIO objectio) {
            this.context = context;
            this.filename = filename;
            this.objectio = objectio;
        }


        @Override
        protected Object doInBackground(Object... params) {

            FileInputStream inputstream = null;
            try {
                File fileDir = context.getFilesDir();
                File f = new File(fileDir, filename);
                inputstream = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(inputstream);
                Object obj = os.readObject();
                os.close();
                inputstream.close();
                return obj;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object resultObj) {
            if (resultObj != null) {
                readerListener.ObjectReaderCallback(resultObj);
            } else {
                Toast.makeText(context, "No object..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface WriterListener{
        public void ObjectWriterCallback(boolean result);
    }
    public interface ReaderListener{
        public void ObjectReaderCallback(Object object);
    }


}

