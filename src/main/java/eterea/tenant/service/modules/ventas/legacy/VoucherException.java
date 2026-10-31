package eterea.tenant.service.modules.ventas.legacy;

public class VoucherException extends RuntimeException {

    public VoucherException(long valueId, String string) {
        super("Cannot find Voucher " + string + " -> " + valueId);
    }

    public VoucherException(String numeroVoucher) {
        super("Cannot find Voucher by numero_voucher " + numeroVoucher);
    }

}
