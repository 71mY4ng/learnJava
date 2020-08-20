package com.company.demo;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CodecTest {

     private byte[] crc16Data = new byte[2];

     byte[] data = new byte[] {
             (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x00,
             (byte) 0x01, (byte) 0x00, (byte) 0x18, (byte) 0x00,
             (byte) 0x65, (byte) 0x12, (byte) 0x22,
             (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x64,
             (byte) 0x00, (byte) 0xA6, (byte) 0xFF, (byte) 0xFF,
             (byte) 0xFF, (byte) 0x01, (byte) 0x36, (byte) 0x42,
             (byte) 0x00, (byte) 0x53, (byte) 0x6A, (byte) 0x52,
             (byte) 0x02, (byte) 0x50, (byte) 0x00, (byte) 0x50,
             (byte) 0x00, (byte) 0xE5, (byte) 0x8E
     };

     byte[] data3 = new byte[] {
             (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x00,
             (byte) 0x83, (byte) 0x00, (byte) 0x18, (byte) 0x00,
             (byte) 0x56, (byte) 0xD6, (byte) 0x22, (byte) 0x01,
             (byte) 0x00, (byte) 0x00, (byte) 0x64, (byte) 0x00,
             (byte) 0xAB, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
             (byte) 0x01, (byte) 0x30, (byte) 0xDC, (byte) 0x00,
             (byte) 0x51, (byte) 0xA2, (byte) 0xC0, (byte) 0x07,
             (byte) 0x50, (byte) 0x00, (byte) 0xFF, (byte) 0xFF,
             (byte) 0xD9, (byte) 0xAA
     };

     byte[] data2 = new byte[] {
             (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x00,
             (byte) 0x62, (byte) 0x00, (byte) 0x18, (byte) 0x00,
             (byte) 0x56, (byte) 0xd6, (byte) 0x22, (byte) 0x01,
             (byte) 0x10, (byte) 0x00, (byte) 0x64, (byte) 0x00,
             (byte) 0xa6, (byte) 0xff, (byte) 0xff, (byte) 0xff,
             (byte) 0x01, (byte) 0x2a, (byte) 0xdc, (byte) 0x00,
             (byte) 0x51, (byte) 0xa2, (byte) 0xc0, (byte) 0x07,
             (byte) 0x1b, (byte) 0x00, (byte) 0xff, (byte) 0xff,
             (byte) 0x20, (byte) 0x51
     };

//     String s = "010201006200180056d6220110006400a6ffffff012adc0051a2c0071b00ffff2051";
//     byte[] data2 = Hex.decodeHex(s.toCharArray());

     @Test
     public void testCrc16() {
//          data2 = data3;

          decode(data2);

          byte[] vendor = data2;
          final long crcRes = getCRC16ModBus(vendor, vendor.length - 2);
          System.out.println("查表法 crcRes: " + Long.toHexString(crcRes));

          final int crc16ModBus = getCRC(vendor, vendor.length - 2);

          int crcarr = modRTUCRC(vendor, vendor.length - 2);
          System.out.println("crc16 modbus rtu: " + Integer.toHexString(crc16ModBus));
          System.out.println("crc16 arr " + Integer.toHexString(crcarr));
          System.out.println("crc16 redisson: " + crc16_redisson(vendor, vendor.length -2));
          System.out.println("crc16 company given: " + crc16_company(vendor, vendor.length -2));

          final long crcData = byte2Int32(crc16Data);
          Assert.assertEquals(crc16ModBus, crcData);
     }

     private void decode(byte[] data) {
          System.out.println(Arrays.toString(data));
          int offset = 0;
          final int length = data.length;

          System.out.println("data length: " + length);
          System.out.println("协议版本：" + Arrays.toString(readBytes(data, offset, 1)));
          System.out.println("功能码: " + Arrays.toString(readBytes(data, offset += 1, 1)));
          System.out.println("终端ID: " + Arrays.toString(readBytes(data, offset += 1, 2)));
          System.out.println("消息序号: " + Arrays.toString(readBytes(data, offset += 2, 2)));
          System.out.println("消息长度: " + Arrays.toString(readBytes(data, offset += 2, 2)));
          final byte[] productSn = readBytes(data, offset += 2, 4);
          System.out.println("产品SN: " + Arrays.toString(productSn));

          System.out.println("终端状态: " + Arrays.toString(readBytes(data, offset += 4, 2)));
          System.out.println("电池剩余容量: " + Arrays.toString(readBytes(data, offset += 2, 1)));
          System.out.println("保留字节: " + Arrays.toString(readBytes(data, offset += 1, 1)));
          System.out.println("强度信号: " + Arrays.toString(readBytes(data, offset += 1, 4)));
          System.out.println("信号覆盖等级: "+  Arrays.toString(readBytes(data, offset += 4, 1)));
          System.out.println("信噪比: "+        Arrays.toString(readBytes(data, offset += 1, 1)));
          System.out.println("小区PCI: "+       Arrays.toString(readBytes(data, offset += 1, 2)));
          System.out.println("CellID: "+        Arrays.toString(readBytes(data, offset += 2, 4)));
          System.out.println("binADepth: "+     Arrays.toString(readBytes(data, offset += 4, 2)));
          System.out.println("binBDepth: "+     Arrays.toString(readBytes(data, offset += 2, 2)));
          byte[] crcBytes = readBytes(data, offset += 2, 2);
          this.crc16Data = crcBytes;
          System.out.println("CRC: "+ Arrays.toString(crcBytes));
     }

     static byte[] readBytes(byte[] data, int offset, int len) {
          byte[] a = new byte[len];

          System.arraycopy(data, offset, a, 0, len);

          System.out.println("[debug] - read len: " + a.length + ", read offset: " + offset);
          return a;
     }

     static long byte2Int32(byte[] data) {
          final long lo = data[1] & 0xff;
          final long hi = (data[0] & 0xff);

          return (hi << 8 | lo);
     }

     private static final int[] LOOKUP_TABLE = new int[]{0, 4129, 8258, 12387, 16516, 20645, 24774, 28903, 33032, 37161, 41290, 45419, 49548, 53677, 57806, 61935, 4657, 528, 12915, 8786, 21173, 17044, 29431, 25302, 37689, 33560, 45947, 41818, 54205, 50076, 62463, 58334, 9314, 13379, 1056, 5121, 25830, 29895, 17572, 21637, 42346, 46411, 34088, 38153, 58862, 62927, 50604, 54669, 13907, 9842, 5649, 1584, 30423, 26358, 22165, 18100, 46939, 42874, 38681, 34616, 63455, 59390, 55197, 51132, 18628, 22757, 26758, 30887, 2112, 6241, 10242, 14371, 51660, 55789, 59790, 63919, 35144, 39273, 43274, 47403, 23285, 19156, 31415, 27286, 6769, 2640, 14899, 10770, 56317, 52188, 64447, 60318, 39801, 35672, 47931, 43802, 27814, 31879, 19684, 23749, 11298, 15363, 3168, 7233, 60846, 64911, 52716, 56781, 44330, 48395, 36200, 40265, 32407, 28342, 24277, 20212, 15891, 11826, 7761, 3696, 65439, 61374, 57309, 53244, 48923, 44858, 40793, 36728, 37256, 33193, 45514, 41451, 53516, 49453, 61774, 57711, 4224, 161, 12482, 8419, 20484, 16421, 28742, 24679, 33721, 37784, 41979, 46042, 49981, 54044, 58239, 62302, 689, 4752, 8947, 13010, 16949, 21012, 25207, 29270, 46570, 42443, 38312, 34185, 62830, 58703, 54572, 50445, 13538, 9411, 5280, 1153, 29798, 25671, 21540, 17413, 42971, 47098, 34713, 38840, 59231, 63358, 50973, 55100, 9939, 14066, 1681, 5808, 26199, 30326, 17941, 22068, 55628, 51565, 63758, 59695, 39368, 35305, 47498, 43435, 22596, 18533, 30726, 26663, 6336, 2273, 14466, 10403, 52093, 56156, 60223, 64286, 35833, 39896, 43963, 48026, 19061, 23124, 27191, 31254, 2801, 6864, 10931, 14994, 64814, 60687, 56684, 52557, 48554, 44427, 40424, 36297, 31782, 27655, 23652, 19525, 15522, 11395, 7392, 3265, 61215, 65342, 53085, 57212, 44955, 49082, 36825, 40952, 28183, 32310, 20053, 24180, 11923, 16050, 3793, 7920};

     public static int crc16_redisson(byte[] bytes, int len) {
          int crc = 0;
          byte[] var2 = bytes;
          int var3 = len;

          for(int var4 = 0; var4 < var3; ++var4) {
               byte b = var2[var4];
               crc = crc << 8 ^ LOOKUP_TABLE[(crc >>> 8 ^ b & 255) & 255];
          }

          return crc & '\uffff';
     }

     static byte[] CRC16Table = new byte[] {
             (byte) 0x0000, (byte) 0xC0C1, (byte) 0xC181, (byte) 0x0140, (byte) 0xC301, (byte) 0x03C0, (byte) 0x0280, (byte) 0xC241,
             (byte) 0xC601, (byte) 0x06C0, (byte) 0x0780, (byte) 0xC741, (byte) 0x0500, (byte) 0xC5C1, (byte) 0xC481, (byte) 0x0440,
             (byte) 0xCC01, (byte) 0x0CC0, (byte) 0x0D80, (byte) 0xCD41, (byte) 0x0F00, (byte) 0xCFC1, (byte) 0xCE81, (byte) 0x0E40,
             (byte) 0x0A00, (byte) 0xCAC1, (byte) 0xCB81, (byte) 0x0B40, (byte) 0xC901, (byte) 0x09C0, (byte) 0x0880, (byte) 0xC841,
             (byte) 0xD801, (byte) 0x18C0, (byte) 0x1980, (byte) 0xD941, (byte) 0x1B00, (byte) 0xDBC1, (byte) 0xDA81, (byte) 0x1A40,
             (byte) 0x1E00, (byte) 0xDEC1, (byte) 0xDF81, (byte) 0x1F40, (byte) 0xDD01, (byte) 0x1DC0, (byte) 0x1C80, (byte) 0xDC41,
             (byte) 0x1400, (byte) 0xD4C1, (byte) 0xD581, (byte) 0x1540, (byte) 0xD701, (byte) 0x17C0, (byte) 0x1680, (byte) 0xD641,
             (byte) 0xD201, (byte) 0x12C0, (byte) 0x1380, (byte) 0xD341, (byte) 0x1100, (byte) 0xD1C1, (byte) 0xD081, (byte) 0x1040,
             (byte) 0xF001, (byte) 0x30C0, (byte) 0x3180, (byte) 0xF141, (byte) 0x3300, (byte) 0xF3C1, (byte) 0xF281, (byte) 0x3240,
             (byte) 0x3600, (byte) 0xF6C1, (byte) 0xF781, (byte) 0x3740, (byte) 0xF501, (byte) 0x35C0, (byte) 0x3480, (byte) 0xF441,
             (byte) 0x3C00, (byte) 0xFCC1, (byte) 0xFD81, (byte) 0x3D40, (byte) 0xFF01, (byte) 0x3FC0, (byte) 0x3E80, (byte) 0xFE41,
             (byte) 0xFA01, (byte) 0x3AC0, (byte) 0x3B80, (byte) 0xFB41, (byte) 0x3900, (byte) 0xF9C1, (byte) 0xF881, (byte) 0x3840,
             (byte) 0x2800, (byte) 0xE8C1, (byte) 0xE981, (byte) 0x2940, (byte) 0xEB01, (byte) 0x2BC0, (byte) 0x2A80, (byte) 0xEA41,
             (byte) 0xEE01, (byte) 0x2EC0, (byte) 0x2F80, (byte) 0xEF41, (byte) 0x2D00, (byte) 0xEDC1, (byte) 0xEC81, (byte) 0x2C40,
             (byte) 0xE401, (byte) 0x24C0, (byte) 0x2580, (byte) 0xE541, (byte) 0x2700, (byte) 0xE7C1, (byte) 0xE681, (byte) 0x2640,
             (byte) 0x2200, (byte) 0xE2C1, (byte) 0xE381, (byte) 0x2340, (byte) 0xE101, (byte) 0x21C0, (byte) 0x2080, (byte) 0xE041,
             (byte) 0xA001, (byte) 0x60C0, (byte) 0x6180, (byte) 0xA141, (byte) 0x6300, (byte) 0xA3C1, (byte) 0xA281, (byte) 0x6240,
             (byte) 0x6600, (byte) 0xA6C1, (byte) 0xA781, (byte) 0x6740, (byte) 0xA501, (byte) 0x65C0, (byte) 0x6480, (byte) 0xA441,
             (byte) 0x6C00, (byte) 0xACC1, (byte) 0xAD81, (byte) 0x6D40, (byte) 0xAF01, (byte) 0x6FC0, (byte) 0x6E80, (byte) 0xAE41,
             (byte) 0xAA01, (byte) 0x6AC0, (byte) 0x6B80, (byte) 0xAB41, (byte) 0x6900, (byte) 0xA9C1, (byte) 0xA881, (byte) 0x6840,
             (byte) 0x7800, (byte) 0xB8C1, (byte) 0xB981, (byte) 0x7940, (byte) 0xBB01, (byte) 0x7BC0, (byte) 0x7A80, (byte) 0xBA41,
             (byte) 0xBE01, (byte) 0x7EC0, (byte) 0x7F80, (byte) 0xBF41, (byte) 0x7D00, (byte) 0xBDC1, (byte) 0xBC81, (byte) 0x7C40,
             (byte) 0xB401, (byte) 0x74C0, (byte) 0x7580, (byte) 0xB541, (byte) 0x7700, (byte) 0xB7C1, (byte) 0xB681, (byte) 0x7640,
             (byte) 0x7200, (byte) 0xB2C1, (byte) 0xB381, (byte) 0x7340, (byte) 0xB101, (byte) 0x71C0, (byte) 0x7080, (byte) 0xB041,
             (byte) 0x5000, (byte) 0x90C1, (byte) 0x9181, (byte) 0x5140, (byte) 0x9301, (byte) 0x53C0, (byte) 0x5280, (byte) 0x9241,
             (byte) 0x9601, (byte) 0x56C0, (byte) 0x5780, (byte) 0x9741, (byte) 0x5500, (byte) 0x95C1, (byte) 0x9481, (byte) 0x5440,
             (byte) 0x9C01, (byte) 0x5CC0, (byte) 0x5D80, (byte) 0x9D41, (byte) 0x5F00, (byte) 0x9FC1, (byte) 0x9E81, (byte) 0x5E40,
             (byte) 0x5A00, (byte) 0x9AC1, (byte) 0x9B81, (byte) 0x5B40, (byte) 0x9901, (byte) 0x59C0, (byte) 0x5880, (byte) 0x9841,
             (byte) 0x8801, (byte) 0x48C0, (byte) 0x4980, (byte) 0x8941, (byte) 0x4B00, (byte) 0x8BC1, (byte) 0x8A81, (byte) 0x4A40,
             (byte) 0x4E00, (byte) 0x8EC1, (byte) 0x8F81, (byte) 0x4F40, (byte) 0x8D01, (byte) 0x4DC0, (byte) 0x4C80, (byte) 0x8C41,
             (byte) 0x4400, (byte) 0x84C1, (byte) 0x8581, (byte) 0x4540, (byte) 0x8701, (byte) 0x47C0, (byte) 0x4680, (byte) 0x8641,
             (byte) 0x8201, (byte) 0x42C0, (byte) 0x4380, (byte) 0x8341, (byte) 0x4100, (byte) 0x81C1, (byte) 0x8081, (byte) 0x4040
     };

     public static long crc16_company(byte[] bytes, int len) {
          long uCRC16 = 0xffff;
          int uIndex = 0;

          for (int i = 0; i < len; i++) {
               uIndex = (int) ((uCRC16 & 0xff) ^ (bytes[i] & 0xff));
               uCRC16 = ((uCRC16 >> 8) & 0xff) ^ CRC16Table[uIndex];
          }

          return uCRC16;
     }

     public static int getCRC(byte[] bytes, int len) {
          int CRC = 0xffff;
          int POLYNOMIAL = 0xa001;

          int i, j;
          for (i = 0; i < len; i++) {
               CRC ^= ((int) bytes[i] & 0x00ff);
               for (j = 0; j < 8; j++) {
                    if ((CRC & 0x0001) != 0) {
                         CRC >>= 1;
                         CRC ^= POLYNOMIAL;
                    } else {
                         CRC >>= 1;
                    }
               }
          }
          CRC ^= 0x0000;
          CRC = ( (CRC & 0xFF00) >> 8) | ( (CRC & 0x00FF ) << 8);
          return CRC;
     }

     public static long getCRC16ModBus(byte[] data, int len) {
          byte[] crc16_h = {
                  (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
                  (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
                  (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
                  (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
                  (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
                  (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
                  (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
                  (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
                  (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
                  (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
                  (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
                  (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
                  (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
                  (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
                  (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
                  (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40
          };

          byte[] crc16_l = {
                  (byte) 0x00, (byte) 0xC0, (byte) 0xC1, (byte) 0x01, (byte) 0xC3, (byte) 0x03, (byte) 0x02, (byte) 0xC2, (byte) 0xC6, (byte) 0x06, (byte) 0x07, (byte) 0xC7, (byte) 0x05, (byte) 0xC5, (byte) 0xC4, (byte) 0x04,
                  (byte) 0xCC, (byte) 0x0C, (byte) 0x0D, (byte) 0xCD, (byte) 0x0F, (byte) 0xCF, (byte) 0xCE, (byte) 0x0E, (byte) 0x0A, (byte) 0xCA, (byte) 0xCB, (byte) 0x0B, (byte) 0xC9, (byte) 0x09, (byte) 0x08, (byte) 0xC8,
                  (byte) 0xD8, (byte) 0x18, (byte) 0x19, (byte) 0xD9, (byte) 0x1B, (byte) 0xDB, (byte) 0xDA, (byte) 0x1A, (byte) 0x1E, (byte) 0xDE, (byte) 0xDF, (byte) 0x1F, (byte) 0xDD, (byte) 0x1D, (byte) 0x1C, (byte) 0xDC,
                  (byte) 0x14, (byte) 0xD4, (byte) 0xD5, (byte) 0x15, (byte) 0xD7, (byte) 0x17, (byte) 0x16, (byte) 0xD6, (byte) 0xD2, (byte) 0x12, (byte) 0x13, (byte) 0xD3, (byte) 0x11, (byte) 0xD1, (byte) 0xD0, (byte) 0x10,
                  (byte) 0xF0, (byte) 0x30, (byte) 0x31, (byte) 0xF1, (byte) 0x33, (byte) 0xF3, (byte) 0xF2, (byte) 0x32, (byte) 0x36, (byte) 0xF6, (byte) 0xF7, (byte) 0x37, (byte) 0xF5, (byte) 0x35, (byte) 0x34, (byte) 0xF4,
                  (byte) 0x3C, (byte) 0xFC, (byte) 0xFD, (byte) 0x3D, (byte) 0xFF, (byte) 0x3F, (byte) 0x3E, (byte) 0xFE, (byte) 0xFA, (byte) 0x3A, (byte) 0x3B, (byte) 0xFB, (byte) 0x39, (byte) 0xF9, (byte) 0xF8, (byte) 0x38,
                  (byte) 0x28, (byte) 0xE8, (byte) 0xE9, (byte) 0x29, (byte) 0xEB, (byte) 0x2B, (byte) 0x2A, (byte) 0xEA, (byte) 0xEE, (byte) 0x2E, (byte) 0x2F, (byte) 0xEF, (byte) 0x2D, (byte) 0xED, (byte) 0xEC, (byte) 0x2C,
                  (byte) 0xE4, (byte) 0x24, (byte) 0x25, (byte) 0xE5, (byte) 0x27, (byte) 0xE7, (byte) 0xE6, (byte) 0x26, (byte) 0x22, (byte) 0xE2, (byte) 0xE3, (byte) 0x23, (byte) 0xE1, (byte) 0x21, (byte) 0x20, (byte) 0xE0,
                  (byte) 0xA0, (byte) 0x60, (byte) 0x61, (byte) 0xA1, (byte) 0x63, (byte) 0xA3, (byte) 0xA2, (byte) 0x62, (byte) 0x66, (byte) 0xA6, (byte) 0xA7, (byte) 0x67, (byte) 0xA5, (byte) 0x65, (byte) 0x64, (byte) 0xA4,
                  (byte) 0x6C, (byte) 0xAC, (byte) 0xAD, (byte) 0x6D, (byte) 0xAF, (byte) 0x6F, (byte) 0x6E, (byte) 0xAE, (byte) 0xAA, (byte) 0x6A, (byte) 0x6B, (byte) 0xAB, (byte) 0x69, (byte) 0xA9, (byte) 0xA8, (byte) 0x68,
                  (byte) 0x78, (byte) 0xB8, (byte) 0xB9, (byte) 0x79, (byte) 0xBB, (byte) 0x7B, (byte) 0x7A, (byte) 0xBA, (byte) 0xBE, (byte) 0x7E, (byte) 0x7F, (byte) 0xBF, (byte) 0x7D, (byte) 0xBD, (byte) 0xBC, (byte) 0x7C,
                  (byte) 0xB4, (byte) 0x74, (byte) 0x75, (byte) 0xB5, (byte) 0x77, (byte) 0xB7, (byte) 0xB6, (byte) 0x76, (byte) 0x72, (byte) 0xB2, (byte) 0xB3, (byte) 0x73, (byte) 0xB1, (byte) 0x71, (byte) 0x70, (byte) 0xB0,
                  (byte) 0x50, (byte) 0x90, (byte) 0x91, (byte) 0x51, (byte) 0x93, (byte) 0x53, (byte) 0x52, (byte) 0x92, (byte) 0x96, (byte) 0x56, (byte) 0x57, (byte) 0x97, (byte) 0x55, (byte) 0x95, (byte) 0x94, (byte) 0x54,
                  (byte) 0x9C, (byte) 0x5C, (byte) 0x5D, (byte) 0x9D, (byte) 0x5F, (byte) 0x9F, (byte) 0x9E, (byte) 0x5E, (byte) 0x5A, (byte) 0x9A, (byte) 0x9B, (byte) 0x5B, (byte) 0x99, (byte) 0x59, (byte) 0x58, (byte) 0x98,
                  (byte) 0x88, (byte) 0x48, (byte) 0x49, (byte) 0x89, (byte) 0x4B, (byte) 0x8B, (byte) 0x8A, (byte) 0x4A, (byte) 0x4E, (byte) 0x8E, (byte) 0x8F, (byte) 0x4F, (byte) 0x8D, (byte) 0x4D, (byte) 0x4C, (byte) 0x8C,
                  (byte) 0x44, (byte) 0x84, (byte) 0x85, (byte) 0x45, (byte) 0x87, (byte) 0x47, (byte) 0x46, (byte) 0x86, (byte) 0x82, (byte) 0x42, (byte) 0x43, (byte) 0x83, (byte) 0x41, (byte) 0x81, (byte) 0x80, (byte) 0x40
          };

          int ucCRCHi = 0xff;
          int ucCRCLo = 0xff;
          int uIndex;
          for (int i = 0; i < len; i++) {
               uIndex = (ucCRCHi ^ data[i]) & 0x00ff;
               ucCRCHi = ucCRCLo ^ crc16_h[uIndex];
               ucCRCLo = crc16_l[uIndex];
          }

//          //高低位互换，输出符合相关工具对Modbus CRC16的运算
//          crc = ((crc & 0xFF00) >> 8) | ((crc & 0x00FF) << 8);
          return ((ucCRCHi << 8) & 0xffff)| ((ucCRCLo & 0xffff));
     }

     // Compute the MODBUS RTU CRC
     // https://ctlsys.com/support/how_to_compute_the_modbus_rtu_message_crc/
     int modRTUCRC(byte[] buf, int len) {
          int crc = 0xFFFF;

          for (int pos = 0; pos < len; pos++) {
               crc ^= buf[pos] & 0xff;          // XOR byte into least sig. byte of crc

               for (int i = 8; i != 0; i--) {    // Loop over each bit
                    if ((crc & 0x0001) != 0) {      // If the LSB is set
                         crc >>= 1;                    // Shift right and XOR 0xA001
                         crc ^= 0xA001;
                    }
                    else                            // Else LSB is not set
                         crc >>= 1;                    // Just shift right
               }
          }
          // Note, this number has low and high bytes swapped, so use it accordingly (or swap bytes)
          return crc;
     }
}
