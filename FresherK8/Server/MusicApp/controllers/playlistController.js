
const model = require('../models/playlistModel.js')

const getListPlaylistController = async (req, res) => {

  try {
    const playlists = await model.getListPlaylist()


    // Tạo một đối tượng Map để lưu trữ các playlist theo tên
    const playlistMap = new Map();

    // Duyệt qua danh sách playlists và gộp chung name_artist cho các playlist cùng tên
    playlists.forEach(playlist => {
      const { playlist_id, playlist_name, playlist_image, name_artist } = playlist;
      if (playlistMap.has(playlist_name)) {
        // Nếu playlist_name đã tồn tại trong Map, cập nhật name_artist
        const existingPlaylist = playlistMap.get(playlist_name);

        existingPlaylist.name_artist += `, ${name_artist}`;
      } else {
        // Nếu playlist_name chưa tồn tại trong Map, thêm mới

        playlistMap.set(playlist_name, { playlist_id, playlist_name, playlist_image, name_artist });
      }
    });

    // Chuyển đổi Map thành mảng và gửi về client
    const mergedPlaylists = Array.from(playlistMap.values());

    res.json({ status: 200, playlists: mergedPlaylists });

  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

const getListPlaylistMoodTodayController = async (req, res) => {

  try {
    const playlists = await model.getListPlaylistMoodToday()
    res.json({ status: 200, playlists })
  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

// create playlist user
const createPlaylistUserController = async (req, res) => {

  try {
    const { namePlaylist, userId } = req.body

    const playlists = await model.createPlaylistUser(namePlaylist, userId)

    res.json({ status: 200, playlists })

  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

// get playlist love

const getListPlaylistByUserIdController = async (req, res) => {
  try {
    const { userId } = req.params;
    const playlists = await model.getListPlaylistByIdUser(userId);

    // Tạo một đối tượng Map để lưu trữ các playlist theo tên và hình ảnh
    const playlistMap = new Map();

    playlists.forEach(playlist => {
      if (playlistMap.has(playlist.playlist_user_name)) {
        playlistMap.get(playlist.playlist_user_name).name_artist += ", " + playlist.name_artist;
      } else {
        playlistMap.set(playlist.playlist_user_name, { ...playlist });
      }
    });

    // Chuyển đổi Map thành mảng và gửi về client
     const mergedPlaylists = Array.from(playlistMap.values());

    mergedPlaylists.forEach(playlist => {
      playlist.song_image = playlists.find(p => p.playlist_user_name === playlist.playlist_user_name).song_image;
    });
  
    res.json({ status: 200, playlists: mergedPlaylists });
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}


module.exports = {
  getListPlaylistController,
  getListPlaylistMoodTodayController,
  getListPlaylistByUserIdController,
  createPlaylistUserController
}
