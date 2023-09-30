create table address
(id bigint not null auto_increment,
city varchar(255), 
first_name varchar(255),
last_name varchar(255),
mobile varchar(255), 
state varchar(255),
street_address varchar(255),
zip_code varchar(255), 
customerid bigint, 
primary key (id)
) engine=InnoDB




create table admin 
(admin_id bigint not null auto_increment, 
about_us TEXT,
address varchar(255),
admin_name varchar(255),
contact bigint not null,
file mediumblob,
password varchar(255),
privacy_and_polices TEXT, 
role varchar(255), 
website_url varchar(255), 
primary key (admin_id)
) engine=InnoDB




create table cart
(id bigint not null auto_increment,
discounte integer not null,
total_discounted_price integer not null,
total_item integer,
total_price float(53), 
customer_id bigint not null,
primary key (id)
) engine=InnoDB



create table cart_item
(id bigint not null auto_increment,
discounted_price integer,
price integer, quantity integer not null,
size varchar(255), 
cart_id bigint, product_product_id bigint,
primary key (id)
) engine=InnoDB



create table categories
(category_id bigint not null auto_increment,
category_image tinyblob, 
category_names varchar(255),
category_offer varchar(255), 
primary key (category_id)
) engine=InnoDB



create table customer
(customer_id bigint not null auto_increment,
address varchar(255), 
contact_no bigint not null,
customer_name varchar(255),
email varchar(255),
password varchar(255),
primary key (customer_id)
) engine=InnoDB



create table order_item
(id bigint not null auto_increment,
delivery_date datetime(6),
discounted_price integer,
price integer, 
quantity integer not null,
size varchar(255),
user_id bigint,
order_id bigint,
product_product_id bigint,
primary key (id)
) engine=InnoDB


create table orders 
(id bigint not null auto_increment,
created_at datetime(6),
delivery_date datetime(6),
discounte integer, 
order_date datetime(6),
order_id varchar(255),
order_status tinyint,
payment_id varchar(255),
payment_method tinyint,
`razorpay_payment_id​` varchar(255),
razorpay_payment_link_id varchar(255),
razorpay_payment_link_reference_id varchar(255),
razorpay_payment_link_status varchar(255),
status tinyint, 
total_discounted_price integer,
total_item integer not null,
total_price float(53) not null,
customer_customer_id bigint,
shipping_address_id bigint, 
primary key (id)
) engine=InnoDB


create table payment_information 
(customer_id bigint not null,
card_number varchar(255),
cardholder_name varchar(255),
cvv varchar(255),
expiration_date date) engine=InnoDB




create table product 
(product_id bigint not null auto_increment,
brand varchar(255),
color varchar(255),
discount_persent integer,
discounted_price integer,
num_ratings integer,
product_cost float(53) not null,
product_description varchar(255),
product_name varchar(255),
product_offer varchar(255),
product_quantity bigint not null,
category_id bigint,
primary key (product_id)
) engine=InnoDB




create table product_images 
(image_id bigint not null auto_increment,
image_data blob, 
product_id bigint,
primary key (image_id)
) engine=InnoDB







create table rateing 
(id bigint not null auto_increment,
created_at datetime(6),
rating float(53),
customer_id bigint not null,
product_id bigint not null,
primary key (id)
) engine=InnoDB







create table reviews
(review_id bigint not null auto_increment,
rating bigint not null,
reviews varchar(255),
product_id bigint,
primary key (review_id)
) engine=InnoDB