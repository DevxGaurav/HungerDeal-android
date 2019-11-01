package com.hungerdeal

import android.content.Context
import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class Manager(val context:Context) {

    private var task:Boolean= false
    private var info:String= ""
    private var data:String?=null
//    private val baseurl= "https://hungerdeal.herokuapp.com/app/api"
    private var listener:OnCompleteListener?= null
    private val baseurl= "http://192.168.51.139/app/api"

    interface OnCompleteListener{
        fun onComplete(task:Boolean, info:String, data:String?)
    }

    fun addOnCompleteListener(listener: OnCompleteListener) {
        this.listener=listener
    }

    fun saveAppData(data: String):Manager {
        context.getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("appdata", data).commit()
        return this
    }

    fun getAppData(): String {
        return context.getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("appdata", "")!!
    }

    fun getUserLocation(): String? {
        return context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("location", null)
    }

    fun saveUserLocation(location: String): Manager {
        context.getSharedPreferences("User", Context.MODE_PRIVATE).edit().putString("location", location).commit()
        return this
    }

    fun getUserLocationType(): String? {
        return context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("location_type", null)
    }

    fun saveUserLocationType(type: String): Manager {
        context.getSharedPreferences("User", Context.MODE_PRIVATE).edit().putString("location_type", type).commit()
        return this
    }

    fun getUserLocationCity(): String? {
        return context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("location_city", null)
    }

    fun saveUserLocationCity(city: String): Manager {
        context.getSharedPreferences("User", Context.MODE_PRIVATE).edit().putString("location_city", city).commit()
        return this
    }

    fun launchData(): Manager {
        Launch().execute()
        return this
    }

    fun compare(keyword: String, restaurant: String, quantity: Int): Manager {
        Compare(keyword, restaurant, quantity).execute()
        return this
    }


    private inner class Launch: AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String? {
            try {
                val url=URL("$baseurl/home")
                val connection= url.openConnection() as HttpURLConnection
                connection.requestMethod= "POST"
                connection.doOutput= true
                connection.doInput= true
                val outputStream= connection.outputStream
                val writer= BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                val strpath= URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode("id", "UTF-8")
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
                data=null
            }else {
                val json=JSONObject(result)
                info=json.getString("info")
                if (json.getInt("code")<=0) {
                    task=false
                    data=null
                }else {
                    task=true
                    data=json.getString("data")
                    saveAppData(data!!)
                }
            }
            if (listener!==null) {
                listener!!.onComplete(task, info, data)
            }
        }
    }


    private inner class Compare(val keyword:String, val restaurant:String, val quantity:Int): AsyncTask<Void, Void, String>() {
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
                        URLEncoder.encode("d_address", "UTF-8")+"="+URLEncoder.encode(getUserLocation(), "UTF-8")+"&"+
                        URLEncoder.encode("city", "UTF-8")+"="+URLEncoder.encode(getUserLocationCity(), "UTF-8")+"&"+
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
                data=null
            }else {
                task=true
                info=result
                val obj=JSONObject(result)
                data=obj.getString("data")
            }
            if (listener!==null) {
                listener!!.onComplete(task, info, data)
            }
        }
    }
}