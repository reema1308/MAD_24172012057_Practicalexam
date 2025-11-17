val uri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
val mapIntent = Intent(Intent.ACTION_VIEW, uri).setPackage("com.google.android.apps.maps")
startActivity(mapIntent)