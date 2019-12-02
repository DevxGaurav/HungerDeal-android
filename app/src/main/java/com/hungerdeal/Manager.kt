package com.hungerdeal

import android.content.Context
import android.os.AsyncTask
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
    private val baseurl= "https://hungerdeal.herokuapp.com/app/api"
    private val baseurl2= "http://192.168.51.139/app/api"

    interface OnCompleteListener{
        fun onComplete(task:Boolean, info:String, data:String?)
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

    fun getUsername(): String? {
        return context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("username", "")
    }

    fun setUsername(username: String): Manager {
        context.getSharedPreferences("User", Context.MODE_PRIVATE).edit().putString("username", username).commit()
        return this
    }

    fun getEmail(): String? {
        return context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "")
    }

    fun setEmail(email: String): Manager {
        context.getSharedPreferences("User", Context.MODE_PRIVATE).edit().putString("username", email).commit()
        return this
    }

    fun deleteAppData(): Manager {
        context.getSharedPreferences("User", Context.MODE_PRIVATE).edit().clear().commit()
        context.getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().clear().commit()
        return this
    }

    fun launchData(listener:OnCompleteListener?=null): Manager {
        Launch(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        return this
    }

    fun compare(keyword: String, restaurant: String, quantity: Int, r:String, listener:OnCompleteListener?=null): Manager {
        Compare(keyword, restaurant, quantity, r, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        return this
    }

    fun compareCoded(keyword: String, restaurant: String, quantity: Int, r:String, listener:OnCompleteListener?=null): Manager {
        CompareCoded(keyword, restaurant, quantity, r, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        return this
    }


    private inner class Launch(val listener:OnCompleteListener?): AsyncTask<Void, Void, String>() {
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
                listener.onComplete(task, info, data)
            }
        }
    }


    private inner class Compare(val keyword:String, val restaurant:String, val quantity:Int, val r:String, val listener:OnCompleteListener?): AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String? {
            try {
                val url=URL("$baseurl2/search")
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
                        URLEncoder.encode("r", "UTF-8")+"="+URLEncoder.encode(r, "UTF-8")+"&"+
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
                val obj=JSONObject(result)
                info=obj.getString("info")
                if (obj.getInt("code")<=0) {
                    task=false
                    data=null
                }else {
                    task=true
                    data=obj.getString("data")
                }
            }
            if (listener!==null) {
                listener.onComplete(task, info, data)
            }
        }
    }


    private inner class CompareCoded(val keyword:String, val restaurant:String, val quantity:Int, val r:String, val listener:OnCompleteListener?): AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String? {
            try {
                val url=URL("$baseurl/searchcoded")
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
                        URLEncoder.encode("r", "UTF-8")+"="+URLEncoder.encode(r, "UTF-8")+"&"+
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
                val obj=JSONObject(result)
                info=obj.getString("info")
                if (obj.getInt("code")<=0) {
                    task=false
                    data=null
                }else {
                    task=true
                    data=obj.getString("result")
                }
            }
            if (listener!==null) {
                listener.onComplete(task, info, data)
            }
        }
    }
}