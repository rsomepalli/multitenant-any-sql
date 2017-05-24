drop table product;

create table catalog(
 id int,
 category_depth int,
 created_date date,
 updated_date date
);

create table product_type(
 id int,
 variant_count int,
 variant1_name varchar(100),
 variant2_name varchar(100),
 variant3_name varchar(100),
 variant4_name varchar(100),
 variant5_name varchar(100)
)

create table parent_product(
 id int,
 name varchar(100),
 description varchar(1000),
 parentSKU varchar(100),
 imageUrl varchar(255),
 hasMultipleVariants boolean,
 variantNames varchar(1000),
 catetory1 varchar(100),
 catetory2 varchar(100),
 catetory3 varchar(100),
 catetory4 varchar(100),
 catetory5 varchar(100),
 product_type int,
 created_date date,
 updated_date date
);

create table product(
  id int primary key,
  parent_id int,
  sku varchar(100) unique not null,
  upc int unique not null,
  price FLOAT,
  variant1_name varchar(100),
  variant1_value varchar(100),
  variant2_name varchar(100),
  variant2_value varchar(100),
  variant3_name varchar(100),
  variant3_value varchar(100),
  variant4_name varchar(100),
  variant4_value varchar(100),
  variant5_name varchar(100),
  variant5_value varchar(100),
  created_date date,
  updated_date date

)


create table product_inventory(
  product_id int PRIMARY KEY,
  inventory_available int,
  created_date date,
  updated_date date
)

create table product_supply_pipeline(
  order_id int,
  vendor varchar(100)
  product_id int,
  quantity int,
  ordered boolean default false,
  ordered_date date,
  shipped boolean default false,
  shipped_date date,
  received boolean default false,
  received_date date,
  planned_recived_date date,
  inventory_updated boolean default false,
  created_date date,
  updated_date date
)

create table merchant(
 id int,
)

create table event(
 id int,
 merchant_id int,
 created_date date,
 updated_date date
)

create table event_proposal(
 id int,
 event_id int,
 merchant_id int,
 product_id int,
 quantity_offered int,
 quantity_allocated int,
 offer_price float,
 suggested_retail_price float,
 created_date date,
 updated_date date
 
)


create table event_sales(
 event_id int,
 sale_date date,
 product_id int,
 quantity int,
 created_date date,
 updated_date date
)



