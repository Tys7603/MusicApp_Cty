package com.example.musicapp.screen.lyrics

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicapp.R
import com.example.musicapp.data.model.Lyric
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.ActivityLyricBinding
import com.example.musicapp.screen.lyrics.adapter.LyricsAdapter
import com.example.musicapp.service.MusicService
import com.example.musicapp.shared.extension.setAdapterLinearVertical
import com.example.musicapp.shared.utils.constant.Constant

class LyricActivity : AppCompatActivity() {
    private var isServiceBound = false // kiểm tra kết nối service
    private var musicService: MusicService? = null
    private var lyricsAdapter =  LyricsAdapter()
    private var currentLyricIndex = 0

    private val binding by lazy {
        ActivityLyricBinding.inflate(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lyricsList = listOf(
            Lyric(5020, "(Maiki 'bout to flip, ey)"),
            Lyric(13760, "Low G có c- đẹp và tao nghĩ nó nên được trưng bày trong lồng viện bảo tàng"),
            Lyric(17470, "Ướp nó lạnh xong để vào bảo quản, tiền bảo đảm là cả một đảo vàng"),
            Lyric(20850, "Lúc đấy thì tao chết xừ òi, nhưng con cháu được uống rượu hảo hạng"),
            Lyric(24360, "Còn tiền thừa cho đi tán gái, party linh tinh để nó bảo bạn"),
            Lyric(27400, "Cụ Low G flex, cụ Low G căng"),
            Lyric(29570, "Low G check là cụ Low G chăn"),
            Lyric(31130, "Low G đua xe ô tô Civic"),
            Lyric(32910, "Low G nhà vô địch Olympic, về flex"),
            Lyric(35370, "Chúng nó vẫn cứ lại bảo là tao khệnh"),
            Lyric(36950, "Tao bảo là, \"Chuẩn òi!\""),
            Lyric(37920, "Khệnh tự tin, khệnh cá tính"),
            Lyric(39500, "Xong lúc già tao sẽ kể cho con cháu là"),
            Lyric(41650, "Tao là thằng kì cục nhưng được cái đẹp trai"),
            Lyric(43410, "Có duyên, thân thiện, hài hước, cuốn hút"),
            Lyric(45070, "Đỉnh cao, biết lắng nghe, ga lăng, nam tính, giàu"),
            Lyric(48020, "Hết cả hơi"),
            Lyric(49960, "Nói chung là hút gái"),
            Lyric(50880, "Đi tán gái tao hâm hâm dở dở nhưng mà không hiểu sao các em nhắn tao hoài"),
            Lyric(54290, "Đưa một em lên hotel Hàng Bài, em đang cởi áo tự nhiên tao dừng lại"),
            Lyric(57970, "Tao bảo em, \"View Hồ Tây đẹp quá em ạ!\", xong ra cửa sổ rap freestyle"),
            Lyric(61300, "Người Hà Nội nên là tính tao cục và đôi khi em ấy hứng lúc tao chửi bậy (ơ, thật à?)"),
            Lyric(66930, "Hơi kì nhưng mà đúng đấy (mm)"),
            Lyric(68210, "Tao thấy cứng và nó đứng lúc tao ngủ dậy"),
            Lyric(70110, "Và nó dài đến mức có thể đu dây (ey)"),
            Lyric(71680, "Nếu tao spam \"đm, vcl\" khi nói chuyện thì cô ấy coi như thần chú á"),
            Lyric(75200, "Em ấy có hai thứ cong, một là mông, hai là giới tính (yeah)"),
            Lyric(78550, "Vòng một là filler bên trong, vừa làm về, trông mới tinh (ah)"),
            Lyric(81950, "Em ấy éo hiểu đang nghe rap, hay đang nghe Low G podcast (ey)"),
            Lyric(85450, "Low G thư giãn, Low G nói về những chủ đề suy ngẫm như là"),
            Lyric(88350, "Low G có c- đẹp và tao nghĩ nó nên được trưng bày trong lồng viện bảo tàng"),
            Lyric(92080, "Ướp nó lạnh xong để vào bảo quản, tiền bảo đảm là cả một đảo vàng"),
            Lyric(95420, "Lúc đấy thì tao chết xừ òi, nhưng con cháu được uống rượu hảo hạng"),
            Lyric(98920, "Còn tiền thừa cho đi tán gái, party linh tinh để nó bảo bạn"),
            Lyric(102330, "Cụ Low G flex, cụ Low G căng"),
            Lyric(104180, "Low G check là cụ Low G chăn"),
            Lyric(105930, "Low G đua xe ô tô Civic"),
            Lyric(107500, "Low G nhà vô địch Olympic, về flex"),
            Lyric(109920, "Chúng nó vẫn cứ lại bảo là tao khệnh"),
            Lyric(111530, "Tao bảo là, \"Chuẩn òi!\""),
            Lyric(112580, "Khệnh tự tin, khệnh cá tính"),
            Lyric(114170, "Xong lúc già tao sẽ kể cho con cháu là"),
            Lyric(116550, "Quen một em đại học đang năm hai rủ vào A25"),
            Lyric(119490, "Body như B52 nhờ uống vitamin C, D hai năm"),
            Lyric(122330, "BMW E52, nhẫn iced out, âm 25 độ F"),
            Lyric(125870, "Fan Low Gờ nên em H \"An Thần\" cả năm vừa rồi mỗi khi em đang high"),
            Lyric(129260, "Một phút đầu tao thơm má em, xong năm phút sau tao chơi má em (cái gì?)"),
            Lyric(132950, "Em muốn cosplay AMEE, nên tao vẽ ria mèo lên má em (à) (meo mèo meo meo)"),
            Lyric(137230, "Ba em nhìn mặt tao thấy khá quen, hỏi tao là thằng nào"),
            Lyric(140050, "Tao vừa nói tên tao, chú ấy cúi đầu xuống chào (sheesh)"),
            Lyric(143250, "Em tao ở nhà penthouse rủ ghệ bem nhau đêm sau nhưng mà không ổn lắm"),
            Lyric(146800, "Suốt ngày truyền năng lượng tích cực nhưng để giấu chuyện gì đấy trong một năm (ey)"),
            Lyric(150160, "Turned out em làm booking bar, bảo sao cứ gặp ai là em ý quý"),
            Lyric(153670, "Lan tỏa tình yêu khắp mọi lúc, xong em lan tỏa luôn con mẹ STDs"),
            Lyric(157610, "Ơ, thế không biết STD là gì à?"),
            Lyric(160880, "Thôi lên mạng search Google đi, đừng bắt người ta phải giải thích"),
            Lyric(163750, "Không lại bảo là, \"Anh Long rap gì mà bậy thế, rap gì mà ghê thế, eo ôi!\""),
            Lyric(168210, "Rap gì mà STDs các kiểu\""),
            Lyric(170630, "Low G có c- đẹp và tao nghĩ nó nên được trưng bày trong lồng viện bảo tàng")
        )


        lyricsAdapter.submitList(lyricsList)
        binding.rcvLyric.setAdapterLinearVertical(lyricsAdapter)


//        musicService?.setOnCompletionListener {
//            // Reset khi phát xong
//            currentLyricIndex = 0
//            lyricsAdapter.highlightedPosition = -1
//            lyricsAdapter.notifyDataSetChanged()
//        }

//        musicService?.start()
//        syncLyricsWithMusic()

        initValue()
        initViewModel()
    }

    private fun initValue() {
        val song = intent.getParcelableExtra<Song>(Constant.KEY_INTENT_ITEM)
        binding.song = song
    }

    private fun initViewModel() {
        TODO("Not yet implemented")
    }

    private fun syncLyricsWithMusic() {
        val handler = Handler()
        handler.post(object : Runnable {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                val currentPosition = musicService?.getCurrentPosition()?.toLong()
                if (currentLyricIndex < lyricsAdapter.itemCount && currentPosition!! >= lyricsAdapter.currentList[currentLyricIndex].startTimeMs) {
                    // Cập nhật vị trí lyric hiện tại
                    lyricsAdapter.notifyItemChanged(lyricsAdapter.highlightedPosition)
                    lyricsAdapter.highlightedPosition = currentLyricIndex
                    lyricsAdapter.notifyItemChanged(currentLyricIndex)
                    // Chuyển sang lyric tiếp theo
                    currentLyricIndex++
                    // Scroll to the highlighted lyric
                    binding.rcvLyric.scrollToPosition(currentLyricIndex)
                }
                handler.postDelayed(this, 100) // Kiểm tra mỗi 100ms
            }
        })
    }

    private val serviceConnection = object : ServiceConnection {
        // kết nối thành công lấy được đối tượng IBinder để try cập music service
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.LocalBinder
            musicService = binder.getService()
            isServiceBound = true
        }

        // ngắt kết nối music service
        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MusicService::class.java)
        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
        musicService = null
    }
}