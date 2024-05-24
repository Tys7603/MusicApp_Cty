const queryDatabase = require("../database/database.js");

const searchModel = async (keyword) => {
  const query = `
  SELECT 'song' AS type, s.song_id as id, s.song_name as name, s.song_image as image, s.song_url as url, a.name_artist as artist, NULL as song_count
  FROM song as s
  INNER JOIN Album as a ON s.album_id = a.album_id
  WHERE s.song_name LIKE CONCAT('%', ?, '%')

  UNION

  SELECT 'playlist' AS type, pl.playlist_id as id, pl.playlist_name as name, pl.playlist_image as image, NULL as url, NULL as artist, COUNT(s.song_id) as song_count
  FROM Playlist as pl
  LEFT JOIN Song as s ON s.playlist_id = pl.playlist_id
  WHERE pl.playlist_name LIKE CONCAT('%', ?, '%')
  GROUP BY pl.playlist_id

  UNION

  SELECT 'music_video' AS type, mv.music_video_id as id, mv.music_video_name as name, ar.artist_image as image, mv.music_video_time as url, ar.artist_name as artist, mv.music_video_image as song_count
  FROM Music_Video as mv
  INNER JOIN Artist as ar ON mv.artist_id = ar.artist_id
  WHERE mv.music_video_name LIKE CONCAT('%', ?, '%')

  UNION

  SELECT 'album' AS type, a.album_id as id, a.album_name as name, a.album_image as image, null as url, a.name_artist as artist, COUNT(s.song_id) as song_count
  FROM Album as a
  LEFT JOIN Song as s ON s.album_id = a.album_id
  WHERE a.album_name LIKE CONCAT('%', ?, '%')
  GROUP BY a.album_id
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
