package het_classification;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAccessObject {
    public static ObservableList<DataEntry> searchEntry(String field, String value) throws SQLException, ClassNotFoundException {
        String sql = "select * from sample_data where " + field + " like '%" + value + "%' order by id";

        try {
            ResultSet rs = DBUtil.executeQuery(sql);
            return getEntriesFromResultSet(rs);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while searching " + value + " under column " + field + ".");
            throw e;
        }
    }

    public static ObservableList<DataEntry> searchEntries() throws SQLException, ClassNotFoundException {
        String sql = "select * from sample_data order by id";

        try {
            ResultSet rs = DBUtil.executeQuery(sql);
            return getEntriesFromResultSet(rs);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while searching.");
            throw e;
        }
    }

    private static ObservableList<DataEntry> getEntriesFromResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<DataEntry> entries = FXCollections.observableArrayList();
        while (rs.next()) {
            DataEntry entry = new DataEntry();

            entry.setTimestamp(rs.getTimestamp("time_stamp"));
            entry.setUsername(rs.getString("username"));
            entry.setId(rs.getInt("id"));
            entry.setSample(rs.getString("sample"));
            entry.setControl(rs.getString("control"));
            entry.setWindowId(rs.getString("window_id"));
            entry.setGene(rs.getString("gene"));
            entry.setAvgCnvRatio(rs.getDouble("avg_cnv_ratio"));
            entry.setAvgBowtieBwaRatio(rs.getDouble("avg_bowtie_bwa_ratio"));
            entry.setBbStd(rs.getDouble("bb_std"));
            entry.setCnvRatioStd(rs.getDouble("cnv_ratio_std"));
            entry.setCovStd(rs.getDouble("cov_std"));
            entry.setAvgCov(rs.getDouble("avg_cov"));
            entry.setAvgDupRatio(rs.getDouble("avg_dup_ratio"));
            entry.setGcPerc(rs.getDouble("gc_perc"));
            entry.setAlleleFreq(rs.getDouble("allele_freq"));
            entry.setReadStats(rs.getInt("read_stats"));
            entry.setIsTraining(rs.getBoolean("is_training"));
            entry.setHetClassification(rs.getBoolean("het_classification"));

            entries.add(entry);
        }
        return entries;
    }

    public static void updateHetClassification (Integer id, Boolean status) throws SQLException, ClassNotFoundException {
        String sql = "update sample_data set het_classification = " + status + " where id = " + id;
        try {
            DBUtil.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while updating id: " + id + ".");
            throw e;
        }
    }
}
