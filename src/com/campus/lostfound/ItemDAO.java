package com.campus.lostfound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ItemDAO (Data Access Object) handles all CRUD operations
 * for the 'items' table in MySQL using JDBC PreparedStatement
 * and try-with-resources.
 */
public class ItemDAO {

    /**
     * Helper method to map a ResultSet row to an Item object.
     */
    private Item mapResultSetToItem(ResultSet rs) throws SQLException {
        return new Item(
            rs.getInt("id"),
            rs.getString("item_name"),
            rs.getString("category"),
            rs.getString("description"),
            rs.getString("location"),
            rs.getString("report_type"),
            rs.getString("reported_by"),
            rs.getString("contact_number"),
            rs.getDate("reported_date"),
            rs.getString("status")
        );
    }

    /**
     * 1 & 2. Add a new Lost or Found Item to the database.
     * 
     * @param item Item to insert
     * @return true if inserted successfully, false otherwise
     */
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (item_name, category, description, location, " +
                     "report_type, reported_by, contact_number, reported_date, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getLocation());
            ps.setString(5, item.getReportType());
            ps.setString(6, item.getReportedBy());
            ps.setString(7, item.getContactNumber());
            ps.setDate(8, item.getReportedDate());
            ps.setString(9, item.getStatus());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("[DAO ERROR] Failed to insert item: " + e.getMessage());
            return false;
        }
    }

    /**
     * 3. View All Items recorded in the database.
     * 
     * @return List of all items
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(mapResultSetToItem(rs));
            }

        } catch (SQLException e) {
            System.err.println("[DAO ERROR] Failed to fetch all items: " + e.getMessage());
        }

        return items;
    }

    /**
     * 4. Search Item by primary key ID.
     * 
     * @param id Item ID
     * @return Item object if found, null otherwise
     */
    public Item getItemById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToItem(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[DAO ERROR] Failed to search item by ID (" + id + "): " + e.getMessage());
        }

        return null;
    }

    /**
     * 5. Search Items by Item Name (partial match, case-insensitive).
     * 
     * @param name search keyword
     * @return List of matching items
     */
    public List<Item> getItemsByName(String name) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE LOWER(item_name) LIKE LOWER(?) ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name.trim() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToItem(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[DAO ERROR] Failed to search items by name: " + e.getMessage());
        }

        return items;
    }

    /**
     * 6 & 7. View Items filtered by report type ("LOST" or "FOUND").
     * 
     * @param reportType "LOST" or "FOUND"
     * @return List of matching items
     */
    public List<Item> getItemsByType(String reportType) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE report_type = ? ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reportType.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToItem(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[DAO ERROR] Failed to fetch items by type (" + reportType + "): " + e.getMessage());
        }

        return items;
    }

    /**
     * 8. Update Item Details (Name, Category, Description, Location, Reported By, Contact, Status).
     * 
     * @param item Item containing updated values
     * @return true if update succeeded, false otherwise
     */
    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET item_name = ?, category = ?, description = ?, " +
                     "location = ?, reported_by = ?, contact_number = ?, status = ? " +
                     "WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getLocation());
            ps.setString(5, item.getReportedBy());
            ps.setString(6, item.getContactNumber());
            ps.setString(7, item.getStatus());
            ps.setInt(8, item.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("[DAO ERROR] Failed to update item details: " + e.getMessage());
            return false;
        }
    }

    /**
     * 9. Mark Item as RETURNED (or another status).
     * 
     * @param id Item ID
     * @param newStatus New status to set (e.g. "RETURNED")
     * @return true if updated, false otherwise
     */
    public boolean updateItemStatus(int id, String newStatus) {
        String sql = "UPDATE items SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus.toUpperCase());
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("[DAO ERROR] Failed to update item status: " + e.getMessage());
            return false;
        }
    }

    /**
     * 10. Delete an item from the database by ID.
     * 
     * @param id Item ID
     * @return true if deleted, false otherwise
     */
    public boolean deleteItem(int id) {
        String sql = "DELETE FROM items WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("[DAO ERROR] Failed to delete item with ID (" + id + "): " + e.getMessage());
            return false;
        }
    }
}
