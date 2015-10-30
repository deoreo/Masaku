package twiscode.masakuuser.Model;

/**
 * Created by User on 10/29/2015.
 */
public class ModelVendorMenu {
    String id, menunama, menutotalpenjualan, menuminimalorder, menuimage;
    public ModelVendorMenu(String id, String menunama, String menutotalpenjualan, String menuminimalorder, String menuimage){
        this.id = id;
        this.menunama = menunama;
        this.menutotalpenjualan = menutotalpenjualan;
        this.menuminimalorder = menuminimalorder;
        this.menuimage = menuimage;
    }

    public String getMenuminimalorder() {
        return menuminimalorder;
    }

    public void setMenuminimalorder(String menuminimalorder) {
        this.menuminimalorder = menuminimalorder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenunama() {
        return menunama;
    }

    public void setMenunama(String menunama) {
        this.menunama = menunama;
    }

    public String getMenutotalpenjualan() {
        return menutotalpenjualan;
    }

    public void setMenutotalpenjualan(String menutotalpenjualan) {
        this.menutotalpenjualan = menutotalpenjualan;
    }

    public String getMenuimage() {
        return menuimage;
    }

    public void setMenuimage(String menuimage) {
        this.menuimage = menuimage;
    }
}
