val checkout = Checkout()
checkout.setKeyID("rzp_test_yourkey")
val options = JSONObject()
options.put("name","E-Pharmacy")
options.put("currency","INR")
options.put("amount", (CartManager.total()*100).toInt()) // paise
checkout.open(this, options)