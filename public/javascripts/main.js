var modal = UIkit.modal(".modalSelector");

if ( modal.isActive() ) {
    modal.hide();
} else {
    modal.show();
}