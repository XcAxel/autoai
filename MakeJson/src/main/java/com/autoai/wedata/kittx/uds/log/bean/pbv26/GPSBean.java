// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gps.proto

package com.autoai.wedata.kittx.uds.log.bean.pbv26;

public final class GPSBean {
  private GPSBean() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GPSOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.autoai.wedata.kittx.uds.log.bean.pbv26.GPS)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     *	当前GPS经度
     * </pre>
     *
     * <code>string c_lon = 1;</code>
     */
    java.lang.String getCLon();
    /**
     * <pre>
     *	当前GPS经度
     * </pre>
     *
     * <code>string c_lon = 1;</code>
     */
    com.google.protobuf.ByteString
        getCLonBytes();

    /**
     * <pre>
     *	当前GPS纬度
     * </pre>
     *
     * <code>string c_lat = 2;</code>
     */
    java.lang.String getCLat();
    /**
     * <pre>
     *	当前GPS纬度
     * </pre>
     *
     * <code>string c_lat = 2;</code>
     */
    com.google.protobuf.ByteString
        getCLatBytes();

    /**
     * <pre>
     *	当前GPS高程
     * </pre>
     *
     * <code>string c_alt = 3;</code>
     */
    java.lang.String getCAlt();
    /**
     * <pre>
     *	当前GPS高程
     * </pre>
     *
     * <code>string c_alt = 3;</code>
     */
    com.google.protobuf.ByteString
        getCAltBytes();

    /**
     * <pre>
     *GPS方向​
     * </pre>
     *
     * <code>string c_direct = 4;</code>
     */
    java.lang.String getCDirect();
    /**
     * <pre>
     *GPS方向​
     * </pre>
     *
     * <code>string c_direct = 4;</code>
     */
    com.google.protobuf.ByteString
        getCDirectBytes();

    /**
     * <pre>
     *	当前GPS时间戳
     * </pre>
     *
     * <code>string c_ts = 5;</code>
     */
    java.lang.String getCTs();
    /**
     * <pre>
     *	当前GPS时间戳
     * </pre>
     *
     * <code>string c_ts = 5;</code>
     */
    com.google.protobuf.ByteString
        getCTsBytes();
  }
  /**
   * <pre>
   *gps的PB数据
   * </pre>
   *
   * Protobuf type {@code com.autoai.wedata.kittx.uds.log.bean.pbv26.GPS}
   */
  public  static final class GPS extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.autoai.wedata.kittx.uds.log.bean.pbv26.GPS)
      GPSOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use GPS.newBuilder() to construct.
    private GPS(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private GPS() {
      cLon_ = "";
      cLat_ = "";
      cAlt_ = "";
      cDirect_ = "";
      cTs_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private GPS(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              cLon_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              cLat_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              cAlt_ = s;
              break;
            }
            case 34: {
              java.lang.String s = input.readStringRequireUtf8();

              cDirect_ = s;
              break;
            }
            case 42: {
              java.lang.String s = input.readStringRequireUtf8();

              cTs_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS.class, com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS.Builder.class);
    }

    public static final int C_LON_FIELD_NUMBER = 1;
    private volatile java.lang.Object cLon_;
    /**
     * <pre>
     *	当前GPS经度
     * </pre>
     *
     * <code>string c_lon = 1;</code>
     */
    public java.lang.String getCLon() {
      java.lang.Object ref = cLon_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cLon_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *	当前GPS经度
     * </pre>
     *
     * <code>string c_lon = 1;</code>
     */
    public com.google.protobuf.ByteString
        getCLonBytes() {
      java.lang.Object ref = cLon_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        cLon_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int C_LAT_FIELD_NUMBER = 2;
    private volatile java.lang.Object cLat_;
    /**
     * <pre>
     *	当前GPS纬度
     * </pre>
     *
     * <code>string c_lat = 2;</code>
     */
    public java.lang.String getCLat() {
      java.lang.Object ref = cLat_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cLat_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *	当前GPS纬度
     * </pre>
     *
     * <code>string c_lat = 2;</code>
     */
    public com.google.protobuf.ByteString
        getCLatBytes() {
      java.lang.Object ref = cLat_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        cLat_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int C_ALT_FIELD_NUMBER = 3;
    private volatile java.lang.Object cAlt_;
    /**
     * <pre>
     *	当前GPS高程
     * </pre>
     *
     * <code>string c_alt = 3;</code>
     */
    public java.lang.String getCAlt() {
      java.lang.Object ref = cAlt_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cAlt_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *	当前GPS高程
     * </pre>
     *
     * <code>string c_alt = 3;</code>
     */
    public com.google.protobuf.ByteString
        getCAltBytes() {
      java.lang.Object ref = cAlt_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        cAlt_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int C_DIRECT_FIELD_NUMBER = 4;
    private volatile java.lang.Object cDirect_;
    /**
     * <pre>
     *GPS方向​
     * </pre>
     *
     * <code>string c_direct = 4;</code>
     */
    public java.lang.String getCDirect() {
      java.lang.Object ref = cDirect_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cDirect_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *GPS方向​
     * </pre>
     *
     * <code>string c_direct = 4;</code>
     */
    public com.google.protobuf.ByteString
        getCDirectBytes() {
      java.lang.Object ref = cDirect_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        cDirect_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int C_TS_FIELD_NUMBER = 5;
    private volatile java.lang.Object cTs_;
    /**
     * <pre>
     *	当前GPS时间戳
     * </pre>
     *
     * <code>string c_ts = 5;</code>
     */
    public java.lang.String getCTs() {
      java.lang.Object ref = cTs_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cTs_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *	当前GPS时间戳
     * </pre>
     *
     * <code>string c_ts = 5;</code>
     */
    public com.google.protobuf.ByteString
        getCTsBytes() {
      java.lang.Object ref = cTs_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        cTs_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getCLonBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, cLon_);
      }
      if (!getCLatBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, cLat_);
      }
      if (!getCAltBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, cAlt_);
      }
      if (!getCDirectBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, cDirect_);
      }
      if (!getCTsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, cTs_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getCLonBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, cLon_);
      }
      if (!getCLatBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, cLat_);
      }
      if (!getCAltBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, cAlt_);
      }
      if (!getCDirectBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, cDirect_);
      }
      if (!getCTsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, cTs_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS)) {
        return super.equals(obj);
      }
      com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS other = (com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS) obj;

      if (!getCLon()
          .equals(other.getCLon())) return false;
      if (!getCLat()
          .equals(other.getCLat())) return false;
      if (!getCAlt()
          .equals(other.getCAlt())) return false;
      if (!getCDirect()
          .equals(other.getCDirect())) return false;
      if (!getCTs()
          .equals(other.getCTs())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + C_LON_FIELD_NUMBER;
      hash = (53 * hash) + getCLon().hashCode();
      hash = (37 * hash) + C_LAT_FIELD_NUMBER;
      hash = (53 * hash) + getCLat().hashCode();
      hash = (37 * hash) + C_ALT_FIELD_NUMBER;
      hash = (53 * hash) + getCAlt().hashCode();
      hash = (37 * hash) + C_DIRECT_FIELD_NUMBER;
      hash = (53 * hash) + getCDirect().hashCode();
      hash = (37 * hash) + C_TS_FIELD_NUMBER;
      hash = (53 * hash) + getCTs().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     *gps的PB数据
     * </pre>
     *
     * Protobuf type {@code com.autoai.wedata.kittx.uds.log.bean.pbv26.GPS}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.autoai.wedata.kittx.uds.log.bean.pbv26.GPS)
        com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPSOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS.class, com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS.Builder.class);
      }

      // Construct using com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        cLon_ = "";

        cLat_ = "";

        cAlt_ = "";

        cDirect_ = "";

        cTs_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_descriptor;
      }

      @java.lang.Override
      public com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS getDefaultInstanceForType() {
        return com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS.getDefaultInstance();
      }

      @java.lang.Override
      public com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS build() {
        com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS buildPartial() {
        com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS result = new com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS(this);
        result.cLon_ = cLon_;
        result.cLat_ = cLat_;
        result.cAlt_ = cAlt_;
        result.cDirect_ = cDirect_;
        result.cTs_ = cTs_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS) {
          return mergeFrom((com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS other) {
        if (other == com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS.getDefaultInstance()) return this;
        if (!other.getCLon().isEmpty()) {
          cLon_ = other.cLon_;
          onChanged();
        }
        if (!other.getCLat().isEmpty()) {
          cLat_ = other.cLat_;
          onChanged();
        }
        if (!other.getCAlt().isEmpty()) {
          cAlt_ = other.cAlt_;
          onChanged();
        }
        if (!other.getCDirect().isEmpty()) {
          cDirect_ = other.cDirect_;
          onChanged();
        }
        if (!other.getCTs().isEmpty()) {
          cTs_ = other.cTs_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object cLon_ = "";
      /**
       * <pre>
       *	当前GPS经度
       * </pre>
       *
       * <code>string c_lon = 1;</code>
       */
      public java.lang.String getCLon() {
        java.lang.Object ref = cLon_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cLon_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *	当前GPS经度
       * </pre>
       *
       * <code>string c_lon = 1;</code>
       */
      public com.google.protobuf.ByteString
          getCLonBytes() {
        java.lang.Object ref = cLon_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          cLon_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *	当前GPS经度
       * </pre>
       *
       * <code>string c_lon = 1;</code>
       */
      public Builder setCLon(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        cLon_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	当前GPS经度
       * </pre>
       *
       * <code>string c_lon = 1;</code>
       */
      public Builder clearCLon() {
        
        cLon_ = getDefaultInstance().getCLon();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	当前GPS经度
       * </pre>
       *
       * <code>string c_lon = 1;</code>
       */
      public Builder setCLonBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        cLon_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cLat_ = "";
      /**
       * <pre>
       *	当前GPS纬度
       * </pre>
       *
       * <code>string c_lat = 2;</code>
       */
      public java.lang.String getCLat() {
        java.lang.Object ref = cLat_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cLat_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *	当前GPS纬度
       * </pre>
       *
       * <code>string c_lat = 2;</code>
       */
      public com.google.protobuf.ByteString
          getCLatBytes() {
        java.lang.Object ref = cLat_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          cLat_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *	当前GPS纬度
       * </pre>
       *
       * <code>string c_lat = 2;</code>
       */
      public Builder setCLat(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        cLat_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	当前GPS纬度
       * </pre>
       *
       * <code>string c_lat = 2;</code>
       */
      public Builder clearCLat() {
        
        cLat_ = getDefaultInstance().getCLat();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	当前GPS纬度
       * </pre>
       *
       * <code>string c_lat = 2;</code>
       */
      public Builder setCLatBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        cLat_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cAlt_ = "";
      /**
       * <pre>
       *	当前GPS高程
       * </pre>
       *
       * <code>string c_alt = 3;</code>
       */
      public java.lang.String getCAlt() {
        java.lang.Object ref = cAlt_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cAlt_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *	当前GPS高程
       * </pre>
       *
       * <code>string c_alt = 3;</code>
       */
      public com.google.protobuf.ByteString
          getCAltBytes() {
        java.lang.Object ref = cAlt_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          cAlt_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *	当前GPS高程
       * </pre>
       *
       * <code>string c_alt = 3;</code>
       */
      public Builder setCAlt(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        cAlt_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	当前GPS高程
       * </pre>
       *
       * <code>string c_alt = 3;</code>
       */
      public Builder clearCAlt() {
        
        cAlt_ = getDefaultInstance().getCAlt();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	当前GPS高程
       * </pre>
       *
       * <code>string c_alt = 3;</code>
       */
      public Builder setCAltBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        cAlt_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cDirect_ = "";
      /**
       * <pre>
       *GPS方向​
       * </pre>
       *
       * <code>string c_direct = 4;</code>
       */
      public java.lang.String getCDirect() {
        java.lang.Object ref = cDirect_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cDirect_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *GPS方向​
       * </pre>
       *
       * <code>string c_direct = 4;</code>
       */
      public com.google.protobuf.ByteString
          getCDirectBytes() {
        java.lang.Object ref = cDirect_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          cDirect_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *GPS方向​
       * </pre>
       *
       * <code>string c_direct = 4;</code>
       */
      public Builder setCDirect(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        cDirect_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *GPS方向​
       * </pre>
       *
       * <code>string c_direct = 4;</code>
       */
      public Builder clearCDirect() {
        
        cDirect_ = getDefaultInstance().getCDirect();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *GPS方向​
       * </pre>
       *
       * <code>string c_direct = 4;</code>
       */
      public Builder setCDirectBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        cDirect_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cTs_ = "";
      /**
       * <pre>
       *	当前GPS时间戳
       * </pre>
       *
       * <code>string c_ts = 5;</code>
       */
      public java.lang.String getCTs() {
        java.lang.Object ref = cTs_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cTs_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *	当前GPS时间戳
       * </pre>
       *
       * <code>string c_ts = 5;</code>
       */
      public com.google.protobuf.ByteString
          getCTsBytes() {
        java.lang.Object ref = cTs_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          cTs_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *	当前GPS时间戳
       * </pre>
       *
       * <code>string c_ts = 5;</code>
       */
      public Builder setCTs(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        cTs_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	当前GPS时间戳
       * </pre>
       *
       * <code>string c_ts = 5;</code>
       */
      public Builder clearCTs() {
        
        cTs_ = getDefaultInstance().getCTs();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	当前GPS时间戳
       * </pre>
       *
       * <code>string c_ts = 5;</code>
       */
      public Builder setCTsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        cTs_ = value;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.autoai.wedata.kittx.uds.log.bean.pbv26.GPS)
    }

    // @@protoc_insertion_point(class_scope:com.autoai.wedata.kittx.uds.log.bean.pbv26.GPS)
    private static final com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS();
    }

    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<GPS>
        PARSER = new com.google.protobuf.AbstractParser<GPS>() {
      @java.lang.Override
      public GPS parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new GPS(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<GPS> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GPS> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.autoai.wedata.kittx.uds.log.bean.pbv26.GPSBean.GPS getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\tgps.proto\022*com.autoai.wedata.kittx.uds" +
      ".log.bean.pbv26\"R\n\003GPS\022\r\n\005c_lon\030\001 \001(\t\022\r\n" +
      "\005c_lat\030\002 \001(\t\022\r\n\005c_alt\030\003 \001(\t\022\020\n\010c_direct\030" +
      "\004 \001(\t\022\014\n\004c_ts\030\005 \001(\tB\tB\007GPSBeanb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_GPS_descriptor,
        new java.lang.String[] { "CLon", "CLat", "CAlt", "CDirect", "CTs", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}