package com.hungerdeal

import android.content.Context
import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class Manager(val context:Context) {

    var task:Boolean= false
    var info:String= ""
    val baseurl= "http://192.168.51.139:8080"
    var listener:OnCompleteListener?= null

    interface OnCompleteListener{
        fun onComplete(task:Boolean, info:String)
    }

    fun addOnCompleteListener(listener: OnCompleteListener) {
        this.listener=listener
    }

    fun compare(keyword: String, restaurant: String, location: String, quantity: Int): Manager {
        Compare(keyword, restaurant, location, quantity).execute()
        return this
    }



    inner class Compare(val keyword:String, val restaurant:String, val location:String, val quantity:Int): AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String? {
            try {
                val url=URL("$baseurl/search")
                val connection= url.openConnection() as HttpURLConnection
                connection.requestMethod= "POST"
                connection.doOutput= true
                connection.doInput= true
                val outputStream= connection.outputStream
                val writer= BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                val strpath= URLEncoder.encode("keyword", "UTF-8")+"="+URLEncoder.encode(keyword, "UTF-8")+"&"+
                        URLEncoder.encode("restaurant", "UTF-8")+"="+URLEncoder.encode(restaurant, "UTF-8")+"&"+
                        URLEncoder.encode("d_address", "UTF-8")+"="+URLEncoder.encode(location, "UTF-8")+"&"+
                        URLEncoder.encode("quantity", "UTF-8")+"="+URLEncoder.encode(quantity.toString(), "UTF-8")
                writer.write(strpath)
                writer.flush()
                writer.close()
                outputStream.close()
                val inputstream= connection.inputStream
                val reader= BufferedReader(InputStreamReader(inputstream, "ISO-8859-1"))
                var line:String?= ""
                var result= ""
                while (line!=null) {
                    result+= line
                    line= reader.readLine()
                }
                inputstream.close()
                reader.close()
                connection.disconnect()
                return result
            }catch (e:MalformedURLException) {
                e.printStackTrace()
            } catch (e:IOException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            if (result==null) {
                task=false
                info="Connection Error"
            }else {
                task=true
                info=result
            }
            if (listener!==null) {
                listener!!.onComplete(task, info)
            }
        }
    }
}