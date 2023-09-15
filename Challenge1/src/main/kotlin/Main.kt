import model.Item
import java.util.*
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun loopingAnimation(seconds:Int, onFinish: (()-> Unit)?=null){ //onFinish untuk callback ketika selesai.
    //thread untuk melakukan animasi sesuai parameter
    val e = Executors.newSingleThreadScheduledExecutor()
    var t=0
    e.scheduleAtFixedRate({
        t++
        print('.')
        if(t==seconds){
            e.shutdown() //memberhentikan thread
            println()
            onFinish?.invoke() //memanggil callback
        }
    },0,1,TimeUnit.SECONDS)
}

fun prosesPengiriman(pilihanPengiriman:Int){
    if(pilihanPengiriman==1){
        print("Makananmu sedang dimasak (5 detik) ")
        loopingAnimation(5){
            print("Makananmu sudah siap! Silahkan ambil di resto ya (5 Detik) ")
            loopingAnimation(5){
                print("Pesanan selesai! (3 detik) ")
                loopingAnimation(3)
            }
        }

    }
    else{
        print("Makananmu sedang dimasak (5 detik) ")
        loopingAnimation(5){
            print("Makananmu sudah siap! Driver segera menuju tempatmu! (5 Detik) ")
            loopingAnimation(5){
                print("Driver sampai! Pesenan selesai! (3 detik) ")
                loopingAnimation(3)
            }
        }
    }
}


fun main() {
    //declare menu
    val menu = listOf(
        Item("Ayam Bakar", 50000),
        Item("Ayam Goreng", 40000),
        Item("Ayam Geprek", 40000),
        Item("Sate Usus Ayam", 15000),
        Item("Kulit Ayam Crispy", 5000)
    )
    println("Warung Makan Binar\n\nMenu Makanan")
    menu.forEachIndexed{ index, it->
        println("${index+1}. ${it.Nama} ${NumberFormat.getCurrencyInstance(Locale("id","ID"))
            .format(it.Harga.toLong())}/Porsi")
    }
    print("Pilih menu: ")
    try {
        val pilihan = readln().toInt()-1
        //cetak menu dipilih
        println("Kamu memilih menu $pilihan")
        println("Nama Menu\t: ${menu[pilihan].Nama}")
        println("Harga\t\t: ${NumberFormat.getCurrencyInstance(Locale("id","ID"))
            .format(menu[pilihan].Harga.toLong())}")

        //Pembayaran
        print("Masukan Pembayaran\t: ")
        try {
            val uang = readln().toInt()
            if(uang>=menu[pilihan].Harga){
                println("Terima Kasih, Anda berhasil memesan makanan")
                //pemeriksaan takeaway or delivery
                println("Metode Pengiriman Makanan\n1.Take Away\n2.Delivery")
                try{
                    val pengiriman = readln().toInt()
                    if(pengiriman in 1..2){
                        prosesPengiriman(pengiriman)
                    }
                    else{
                        println("Menu Tidak Tersedia")
                    }
                }
                catch (e:NumberFormatException){
                    println("Your input isn't correct :/")
                }
            }
            else{
                println("Maaf, Pembayaran Anda gagal!")
            }
        }
        catch (e:NumberFormatException){
            println("Your input isn't correct :/")
        }

    }
    //jika input tidak sesuai format yang diberikan
    catch (e:NumberFormatException){
        println("Your input isn't correct :/")
    }
}