package com.example.musicapp.shared.utils.constant

object Constant {
    const val STATUS = 200
    const val TAG_ERROR = "Error"
    const val SONG_COMPLETED = "song_completed"
    const val URL_IMAGE = "https://cdn-icons-png.flaticon.com/512/1053/1053244.png"
    const val KEY_SONG = "song"
    const val KEY_USER = "start_user"
    const val KEY_INTENT_ITEM = "item"
    const val KEY_BUNDLE_ITEM = "bundle"
    const val EMPTY_SONG_MESSAGE = "Chưa nghe bài hát nào"
    const val KEY_PLAY_CLICK = "play_music"
    const val VALUE_DEFAULT = "00:00"
    const val KEY_POSITION = "position"
    const val KEY_POSITION_SONG = "position_song"
    const val KEY_AUTO_RESTART = "auto_restart"
    const val KEY_SHUFFLE = "shuffle"
    const val KEY_DOWN = "Downloading..."
    const val KEY_HAVE_DOWN = "Bài hát đã được tải"
    const val FAILURE = "Thất bại"
    const val CALL_API_ERROR = "Lỗi khi gọi API: "
    const val ACCESS_RULES = "Bạn chưa đồng ý điều khoản của chúng tôi"
    const val LOGIN = "Đăng nhập"
    const val SING_UP = "Đăng ký"
    const val PLAYLIST_USER = "Playlist đã tạo"
    const val PLAYLIST_LOVE = "Playlist yêu thích"
    const val LOGIN_FAILURE = "Đăng nhập thất bại"
    const val SING_UP_FAILURE = "Đăng ký thất bại"
    const val SING_UP_SUCCESS= "Đăng ký thành công"
    const val LOGIN_EMPTY = "Không được bỏ trống"
    const val SING_UP_EQUAL = "Mật khẩu không trùng khớp"
    const val SEN_EMAIL_FAILURE = "Gửi email thất bại"
    const val SEN_EMAIL_SUCCESS = "Gửi email thành công"
    // bỏ enum ở đây
    enum class ClickControllerPlayerUi(){
        ON_BACK,
        ON_NEXT_VIDEO,
        ON_BACK_VIDEO,
    }
}