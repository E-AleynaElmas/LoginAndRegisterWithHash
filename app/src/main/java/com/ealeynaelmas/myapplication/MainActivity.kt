package com.ealeynaelmas.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try{
            val veritabani = this.openOrCreateDatabase("Users", MODE_PRIVATE, null)
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, kullaniciAdi VARCHAR, sifre VARCHAR)")
            //veritabani.execSQL("INSERT INTO kullanici (kullaniciAdi, sifre) VALUES ('Aley', 'Aley123')")
            //veritabani.execSQL("DELETE FROM kullanici")

        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun kaydolButton(view : View){
        val veritabani = this.openOrCreateDatabase("Users", MODE_PRIVATE, null)
        val name = userName.text.toString()
        val pswrd = password.text.toString().hashCode()
        veritabani.execSQL("INSERT INTO users (kullaniciAdi, sifre) VALUES ('${name}', '${pswrd}')")
        Toast.makeText(
            applicationContext,
            "Kayıt Başarılı",
            Toast.LENGTH_SHORT
        ).show()
        createdHash.setText("Oluşturulan Hash Kodu: ${pswrd.toString()}")
    }

    fun girisButton(view : View){
        val name = userName.text.toString()
        val pswrd = password.text.toString().hashCode().toString()

        val veritabani = this.openOrCreateDatabase("Users", MODE_PRIVATE, null)
        val cursor = veritabani.rawQuery("SELECT * FROM users WHERE kullaniciAdi = '${name}'", null)

        val sifreColumnIndex = cursor.getColumnIndex("sifre")

        while(cursor.moveToNext()) {
            if(cursor.getString(sifreColumnIndex).toString() == pswrd) {
                Toast.makeText(
                    applicationContext,
                    "Giriş Başarılı",
                    Toast.LENGTH_SHORT
                ).show()
                enterHash.setText("Oluşturulan Hash Kodu: ${pswrd.toString()}")
                return
            }
        }
        Toast.makeText(
            applicationContext,
            "Böyle bir kullanici bulunamadi",
            Toast.LENGTH_SHORT
        ).show()
        cursor.close()
    }
}