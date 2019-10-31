// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: page.proto

package com.autoai.wedata.kittx.uds.log.bean.pbv26;

public final class PageBean {
  private PageBean() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.autoai.wedata.kittx.uds.log.bean.pbv26.Page)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     *	页面code
     * </pre>
     *
     * <code>string page_code = 1;</code>
     */
    java.lang.String getPageCode();
    /**
     * <pre>
     *	页面code
     * </pre>
     *
     * <code>string page_code = 1;</code>
     */
    com.google.protobuf.ByteString
        getPageCodeBytes();

    /**
     * <pre>
     *	进入页面的时间
     * </pre>
     *
     * <code>string p_into_ts = 2;</code>
     */
    java.lang.String getPIntoTs();
    /**
     * <pre>
     *	进入页面的时间
     * </pre>
     *
     * <code>string p_into_ts = 2;</code>
     */
    com.google.protobuf.ByteString
        getPIntoTsBytes();

    /**
     * <pre>
     *	离开页面的时间
     * </pre>
     *
     * <code>string p_out_ts = 3;</code>
     */
    java.lang.String getPOutTs();
    /**
     * <pre>
     *	离开页面的时间
     * </pre>
     *
     * <code>string p_out_ts = 3;</code>
     */
    com.google.protobuf.ByteString
        getPOutTsBytes();
  }
  /**
   * <pre>
   *page的PB数据
   * </pre>
   *
   * Protobuf type {@code com.autoai.wedata.kittx.uds.log.bean.pbv26.Page}
   */
  public  static final class Page extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.autoai.wedata.kittx.uds.log.bean.pbv26.Page)
      PageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Page.newBuilder() to construct.
    private Page(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Page() {
      pageCode_ = "";
      pIntoTs_ = "";
      pOutTs_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Page(
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

              pageCode_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              pIntoTs_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              pOutTs_ = s;
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
      return com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page.class, com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page.Builder.class);
    }

    public static final int PAGE_CODE_FIELD_NUMBER = 1;
    private volatile java.lang.Object pageCode_;
    /**
     * <pre>
     *	页面code
     * </pre>
     *
     * <code>string page_code = 1;</code>
     */
    public java.lang.String getPageCode() {
      java.lang.Object ref = pageCode_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        pageCode_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *	页面code
     * </pre>
     *
     * <code>string page_code = 1;</code>
     */
    public com.google.protobuf.ByteString
        getPageCodeBytes() {
      java.lang.Object ref = pageCode_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        pageCode_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int P_INTO_TS_FIELD_NUMBER = 2;
    private volatile java.lang.Object pIntoTs_;
    /**
     * <pre>
     *	进入页面的时间
     * </pre>
     *
     * <code>string p_into_ts = 2;</code>
     */
    public java.lang.String getPIntoTs() {
      java.lang.Object ref = pIntoTs_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        pIntoTs_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *	进入页面的时间
     * </pre>
     *
     * <code>string p_into_ts = 2;</code>
     */
    public com.google.protobuf.ByteString
        getPIntoTsBytes() {
      java.lang.Object ref = pIntoTs_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        pIntoTs_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int P_OUT_TS_FIELD_NUMBER = 3;
    private volatile java.lang.Object pOutTs_;
    /**
     * <pre>
     *	离开页面的时间
     * </pre>
     *
     * <code>string p_out_ts = 3;</code>
     */
    public java.lang.String getPOutTs() {
      java.lang.Object ref = pOutTs_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        pOutTs_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *	离开页面的时间
     * </pre>
     *
     * <code>string p_out_ts = 3;</code>
     */
    public com.google.protobuf.ByteString
        getPOutTsBytes() {
      java.lang.Object ref = pOutTs_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        pOutTs_ = b;
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
      if (!getPageCodeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, pageCode_);
      }
      if (!getPIntoTsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, pIntoTs_);
      }
      if (!getPOutTsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, pOutTs_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getPageCodeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, pageCode_);
      }
      if (!getPIntoTsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, pIntoTs_);
      }
      if (!getPOutTsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, pOutTs_);
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
      if (!(obj instanceof com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page)) {
        return super.equals(obj);
      }
      com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page other = (com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page) obj;

      if (!getPageCode()
          .equals(other.getPageCode())) return false;
      if (!getPIntoTs()
          .equals(other.getPIntoTs())) return false;
      if (!getPOutTs()
          .equals(other.getPOutTs())) return false;
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
      hash = (37 * hash) + PAGE_CODE_FIELD_NUMBER;
      hash = (53 * hash) + getPageCode().hashCode();
      hash = (37 * hash) + P_INTO_TS_FIELD_NUMBER;
      hash = (53 * hash) + getPIntoTs().hashCode();
      hash = (37 * hash) + P_OUT_TS_FIELD_NUMBER;
      hash = (53 * hash) + getPOutTs().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parseFrom(
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
    public static Builder newBuilder(com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page prototype) {
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
     *page的PB数据
     * </pre>
     *
     * Protobuf type {@code com.autoai.wedata.kittx.uds.log.bean.pbv26.Page}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.autoai.wedata.kittx.uds.log.bean.pbv26.Page)
        com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.PageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page.class, com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page.Builder.class);
      }

      // Construct using com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page.newBuilder()
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
        pageCode_ = "";

        pIntoTs_ = "";

        pOutTs_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_descriptor;
      }

      @java.lang.Override
      public com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page getDefaultInstanceForType() {
        return com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page.getDefaultInstance();
      }

      @java.lang.Override
      public com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page build() {
        com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page buildPartial() {
        com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page result = new com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page(this);
        result.pageCode_ = pageCode_;
        result.pIntoTs_ = pIntoTs_;
        result.pOutTs_ = pOutTs_;
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
        if (other instanceof com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page) {
          return mergeFrom((com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page other) {
        if (other == com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page.getDefaultInstance()) return this;
        if (!other.getPageCode().isEmpty()) {
          pageCode_ = other.pageCode_;
          onChanged();
        }
        if (!other.getPIntoTs().isEmpty()) {
          pIntoTs_ = other.pIntoTs_;
          onChanged();
        }
        if (!other.getPOutTs().isEmpty()) {
          pOutTs_ = other.pOutTs_;
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
        com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object pageCode_ = "";
      /**
       * <pre>
       *	页面code
       * </pre>
       *
       * <code>string page_code = 1;</code>
       */
      public java.lang.String getPageCode() {
        java.lang.Object ref = pageCode_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          pageCode_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *	页面code
       * </pre>
       *
       * <code>string page_code = 1;</code>
       */
      public com.google.protobuf.ByteString
          getPageCodeBytes() {
        java.lang.Object ref = pageCode_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          pageCode_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *	页面code
       * </pre>
       *
       * <code>string page_code = 1;</code>
       */
      public Builder setPageCode(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        pageCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	页面code
       * </pre>
       *
       * <code>string page_code = 1;</code>
       */
      public Builder clearPageCode() {
        
        pageCode_ = getDefaultInstance().getPageCode();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	页面code
       * </pre>
       *
       * <code>string page_code = 1;</code>
       */
      public Builder setPageCodeBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        pageCode_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object pIntoTs_ = "";
      /**
       * <pre>
       *	进入页面的时间
       * </pre>
       *
       * <code>string p_into_ts = 2;</code>
       */
      public java.lang.String getPIntoTs() {
        java.lang.Object ref = pIntoTs_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          pIntoTs_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *	进入页面的时间
       * </pre>
       *
       * <code>string p_into_ts = 2;</code>
       */
      public com.google.protobuf.ByteString
          getPIntoTsBytes() {
        java.lang.Object ref = pIntoTs_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          pIntoTs_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *	进入页面的时间
       * </pre>
       *
       * <code>string p_into_ts = 2;</code>
       */
      public Builder setPIntoTs(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        pIntoTs_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	进入页面的时间
       * </pre>
       *
       * <code>string p_into_ts = 2;</code>
       */
      public Builder clearPIntoTs() {
        
        pIntoTs_ = getDefaultInstance().getPIntoTs();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	进入页面的时间
       * </pre>
       *
       * <code>string p_into_ts = 2;</code>
       */
      public Builder setPIntoTsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        pIntoTs_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object pOutTs_ = "";
      /**
       * <pre>
       *	离开页面的时间
       * </pre>
       *
       * <code>string p_out_ts = 3;</code>
       */
      public java.lang.String getPOutTs() {
        java.lang.Object ref = pOutTs_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          pOutTs_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *	离开页面的时间
       * </pre>
       *
       * <code>string p_out_ts = 3;</code>
       */
      public com.google.protobuf.ByteString
          getPOutTsBytes() {
        java.lang.Object ref = pOutTs_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          pOutTs_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *	离开页面的时间
       * </pre>
       *
       * <code>string p_out_ts = 3;</code>
       */
      public Builder setPOutTs(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        pOutTs_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	离开页面的时间
       * </pre>
       *
       * <code>string p_out_ts = 3;</code>
       */
      public Builder clearPOutTs() {
        
        pOutTs_ = getDefaultInstance().getPOutTs();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *	离开页面的时间
       * </pre>
       *
       * <code>string p_out_ts = 3;</code>
       */
      public Builder setPOutTsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        pOutTs_ = value;
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


      // @@protoc_insertion_point(builder_scope:com.autoai.wedata.kittx.uds.log.bean.pbv26.Page)
    }

    // @@protoc_insertion_point(class_scope:com.autoai.wedata.kittx.uds.log.bean.pbv26.Page)
    private static final com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page();
    }

    public static com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Page>
        PARSER = new com.google.protobuf.AbstractParser<Page>() {
      @java.lang.Override
      public Page parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Page(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Page> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Page> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.autoai.wedata.kittx.uds.log.bean.pbv26.PageBean.Page getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\npage.proto\022*com.autoai.wedata.kittx.ud" +
      "s.log.bean.pbv26\">\n\004Page\022\021\n\tpage_code\030\001 " +
      "\001(\t\022\021\n\tp_into_ts\030\002 \001(\t\022\020\n\010p_out_ts\030\003 \001(\t" +
      "B\nB\010PageBeanb\006proto3"
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
    internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_autoai_wedata_kittx_uds_log_bean_pbv26_Page_descriptor,
        new java.lang.String[] { "PageCode", "PIntoTs", "POutTs", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
