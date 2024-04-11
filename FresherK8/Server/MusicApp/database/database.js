const mysql = require('mysql');

const pool = mysql.createPool({
    host: 'localhost',  // MySQL server host
    user: 'your_username',  // MySQL username
    password: 'your_password',  // MySQL password
    database: 'your_database',  // MySQL database name
})

// Function to execute SQL queries
function executeQuery(query, values, callback) {

    pool.getConnection((err, connection) => {
        if (err) {
            console.error('Error getting MySQL connection:', err);
            return callback(err, null);
        }

        connection.query(query, values, (err, results) => {
            connection.release(); // Release the connection

            if (err) {
                console.error('Error executing MySQL query:', err);
                return callback(err, null);
            }

            callback(null, results);
        });
    });
}

module.exports =  executeQuery

