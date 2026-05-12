DROP TABLE IF EXISTS public.material_category_link;
DROP TABLE IF EXISTS public.BOM_lines;
DROP TABLE IF EXISTS public.orders;
DROP TABLE IF EXISTS public.quotes;
DROP TABLE IF EXISTS public.inquiries;
DROP TABLE IF EXISTS public.materials;
DROP TABLE IF EXISTS public.material_categories;
DROP TABLE IF EXISTS public.salespersons;
DROP TABLE IF EXISTS public.customers;
DROP TABLE IF EXISTS public.zipcodes;


CREATE TABLE public.zipcodes (
                                 zipcode INT PRIMARY KEY,
                                 city VARCHAR(50) NOT NULL
);

CREATE TABLE public.material_categories (
                                            category_id SERIAL PRIMARY KEY,
                                            category_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE public.materials (
                                  material_id SERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  unit VARCHAR(50) NOT NULL,
                                  price_per_unit DECIMAL(10,2) NOT NULL
);

CREATE TABLE public.material_category_link (
                                               material_id INT REFERENCES public.materials(material_id)
                                                   ON DELETE CASCADE,
                                               category_id INT REFERENCES public.material_categories(category_id)
                                                   ON DELETE CASCADE,
                                               PRIMARY KEY (material_id, category_id)
);

CREATE TABLE public.salespersons (
                                     salesperson_id SERIAL PRIMARY KEY,
                                     firstname VARCHAR(255) NOT NULL,
                                     last_name VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role VARCHAR(50) NOT NULL
);

CREATE TABLE public.customers (
                                  customer_id SERIAL PRIMARY KEY,
                                  firstname VARCHAR(255) NOT NULL,
                                  last_name VARCHAR(255) NOT NULL,
                                  address VARCHAR(255) NOT NULL,
                                  zipcode INT REFERENCES public.zipcodes(zipcode),
                                  email VARCHAR(255) NOT NULL
);

CREATE TABLE public.inquiries (
                                  inquiry_id SERIAL PRIMARY KEY,
                                  customer_id INT REFERENCES public.customers(customer_id),
                                  carport_length INT NOT NULL,
                                  carport_width INT NOT NULL,
                                  roof_type VARCHAR(255) NOT NULL,
                                  slope_roof INT DEFAULT 0,
                                  siding VARCHAR(255) NOT NULL,
                                  shed_length INT NOT NULL,
                                  shed_width INT NOT NULL,
                                  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.quotes (
                               quotation_id SERIAL PRIMARY KEY,
                               inquiry_id INT REFERENCES public.inquiries(inquiry_id),
                               salesperson_id INT REFERENCES public.salespersons(salesperson_id),
                               price DECIMAL(10,2) NOT NULL,
                               status VARCHAR(255) NOT NULL DEFAULT 'pending',
                               version INT NOT NULL DEFAULT 1
);

CREATE TABLE public.orders (
                               order_id SERIAL PRIMARY KEY,
                               quotation_id INT REFERENCES public.quotes(quotation_id),
                               paid BOOLEAN DEFAULT FALSE,
                               released BOOLEAN DEFAULT FALSE,
                               date_of_payment TIMESTAMP,
                               invoice VARCHAR(255)
);

CREATE TABLE public.BOM_lines (
                                  BOM_linjer_id SERIAL PRIMARY KEY,
                                  order_id INT REFERENCES public.orders(order_id),
                                  material_id INT REFERENCES public.materials(material_id),
                                  quantity INT NOT NULL,
                                  length INT,
                                  unit VARCHAR(50) NOT NULL,
                                  section VARCHAR(50) NOT NULL
);
