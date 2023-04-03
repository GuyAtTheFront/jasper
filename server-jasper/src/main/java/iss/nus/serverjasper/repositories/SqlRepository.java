package iss.nus.serverjasper.repositories;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import iss.nus.serverjasper.exceptions.TransactionFailedException;
import iss.nus.serverjasper.models.FormData;

@Repository
public class SqlRepository {

    private final String SQL_INSERT_FILE =
    """
    INSERT INTO files (filename, media_type, content) VALUES (?, ?, ?);
    """;

    private final String SQL_INSERT_FORM_DATA =
    """
    INSERT INTO form_data (id, username, comment, description, file_id) VALUES (?, ?, ?, ?, ?);
    """;

    @Autowired
    JdbcTemplate jdbcTemplate;
    

    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void insertFormData(FormData data) {

        Integer key = this.insertFile(data.getFile());
        
        if (key == -1) throw new TransactionFailedException("failed to insert file");

        Integer inserted = jdbcTemplate.update(SQL_INSERT_FORM_DATA, 
                                                data.getId(),
                                                data.getUsername(), 
                                                data.getComment(), 
                                                data.getDescription(),
                                                key);

        if (inserted != 1) throw new TransactionFailedException("failed to insert form data");
    }


    private Integer insertFile(MultipartFile file) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(SQL_INSERT_FILE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, file.getOriginalFilename());
                ps.setString(2, file.getContentType());
                try {
                    ps.setBinaryStream(3, file.getInputStream());
                } catch (IOException e) {
                    throw new TransactionFailedException(e.getMessage());
                }
                return ps;
            }
        };

        Integer inserted = jdbcTemplate.update(psc, keyHolder);

        return (inserted > 0) ? keyHolder.getKey().intValue() : -1;
        }

}
