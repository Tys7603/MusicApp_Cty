const queryDatabase = require("../database/database.js");

const searchModel = async (keyword) => {
    const query = `
    SELECT 'song' AS type, song_id AS id, song_name AS name
    FROM song
    WHERE song_name LIKE CONCAT('%', ?, '%')
    UNION
    SELECT 'playlist' AS type, playlist_id AS id, playlist_name AS name
    FROM playlist
    WHERE playlist_name LIKE CONCAT('%', ?, '%')
    UNION
    SELECT 'music_video' AS type, music_video_id AS id, music_video_name AS name
    FROM music_video
    WHERE music_video_name LIKE CONCAT('%', ?, '%')
    UNION
    SELECT 'album' AS type, album_id AS id, album_name AS name
    FROM album
    WHERE album_name LIKE CONCAT('%', ?, '%');
  `;
    return await queryDatabase(query, [keyword, keyword, keyword, keyword]);
};

const searchNameAllModel = async () => {
    const query = `
    SELECT playlist_name AS name FROM playlist
    UNION
    SELECT song_name AS name FROM song
    UNION
    SELECT album_name AS name FROM album
    UNION
    SELECT music_video_name AS name FROM music_video;
  `;
    return await queryDatabase(query);
};

module.exports = {
    searchModel,
    searchNameAllModel
}
