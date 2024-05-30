
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
    const defaultImageUrl = "https://iili.io/HlHy9Yx.png";

    const playlists = await model.getListPlaylistByIdUser(userId);

    const updatedPlaylists = playlists.map(playlist => {
      if (!playlist.song_image) {
        playlist.song_image = defaultImageUrl;
      }
      if (!playlist.name_artist) {
        playlist.name_artist = "";
      }
      return playlist;
    });

    res.json({ status: 200, playlists: updatedPlaylists });
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}

// get playlist user song 
const getListSongPlaylistByUserIdController = async (req, res) => {
  try {
    const { playlistUserId } = req.params;

    const songs = await model.getListSongPlaylistByIdUser(playlistUserId);

    res.json({ status: 200, songs });
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}

const deletePlaylistUserByIdController = async (req, res) => {
  try {
    const { playlistUserId } = req.query;

    const listPlaylistUser = JSON.parse(playlistUserId)

    const playlists = await model.deletePlaylistUserById(listPlaylistUser);

    res.json({ status: 200, playlists });
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}

const deletePlaylistLoveByIdController = async (req, res) => {
  try {
    const { playlistLoveId } = req.query;

    console.log(playlistLoveId)

    const listPlaylistLove = JSON.parse(playlistLoveId)

    const playlists = await model.deletePlaylistLoveById(listPlaylistLove);

    res.json({ status: 200, playlists });
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}


const createSongIntoPlaylistByUserIdController = async (req, res) => {
  try {
    const { playlistUserId, songId } = req.body;

    const playlists = await model.createSongIntoPlaylistByUserId(playlistUserId, songId);

    res.json(playlists);
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}

const getListPlaylistLoveByUserIdController = async (req, res) => {

  try {
    const {userId} = req.params    
    const playlists = await model.getListPlaylistLoveByIdUser(userId)

    res.json({ status: 200, playlists });

  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

const inserPlaylistIntoPlaylistLoveByUserIdController = async (req, res) => {
  try {
    const { userId, playlistId } = req.body;

    console.log(userId)
    console.log(playlistId)

    const playlists = await model.inserPlaylistIntoPlaylistLoveByUserId(playlistId, userId);

    res.json(playlists);
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}

module.exports = {
  getListPlaylistController,
  getListPlaylistMoodTodayController,
  getListPlaylistByUserIdController,
  createPlaylistUserController,
  deletePlaylistUserByIdController,
  createSongIntoPlaylistByUserIdController,
  getListPlaylistLoveByUserIdController,
  deletePlaylistLoveByIdController,
  inserPlaylistIntoPlaylistLoveByUserIdController,
  getListSongPlaylistByUserIdController
}
