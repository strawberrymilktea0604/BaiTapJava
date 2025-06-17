CREATE DATABASE QuanLySach;
GO


USE QuanLySach;
GO


CREATE TABLE DMSach (
    MaSach NVARCHAR(10) PRIMARY KEY,
    TenSach NVARCHAR(100) NOT NULL,
    NamXB INT NOT NULL,
    Gia FLOAT NOT NULL,
    SoLuong INT NOT NULL
);
GO


INSERT INTO DMSach (MaSach, TenSach, NamXB, Gia, SoLuong) VALUES
('TH01', N'Lập trình Java', 2010, 140000, 100),
('TH02', N'Lập trình C++', 2021, 125000, 110),
('DT01', N'Điện tử cơ bản', 2018, 110000, 150),
('DT02', N'Vi xử lý', 2022, 105000, 102);
GO


SELECT * FROM DMSach;
GO